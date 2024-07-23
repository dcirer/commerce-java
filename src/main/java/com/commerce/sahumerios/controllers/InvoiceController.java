package com.commerce.sahumerios.controllers;

import com.commerce.sahumerios.entities.Cart;
import com.commerce.sahumerios.entities.Client;
import com.commerce.sahumerios.entities.Invoice;
import com.commerce.sahumerios.entities.Product;
import com.commerce.sahumerios.services.CartsService;
import com.commerce.sahumerios.services.ClientsService;
import com.commerce.sahumerios.services.InvoicesService;
import com.commerce.sahumerios.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/invoices")
public class InvoiceController {
    @Autowired private InvoicesService invoicesService;
    @Autowired private CartsService cartsService;
    @Autowired private ClientsService clientsService;
    @Autowired private ProductsService productsService;

    @PostMapping("/{clientId}")
    public ResponseEntity<Invoice> generateInvoice(@PathVariable Long clientId) {
        Optional<Client> client = clientsService.readOne(clientId);
        if (client.isPresent()) {
            List<Cart> carts = cartsService.readAll();
            double total = 0;
            LocalDateTime now = LocalDateTime.now(); // Fecha y hora actual

            for (Cart cart : carts) {
                if (cart.getClient().getId().equals(clientId) && !cart.isBilled()) {
                    Product product = cart.getProduct();
                    if (cart.getAmoun() <= product.getStock()) {
                        total += product.getPrice() * cart.getAmoun();
                        product.setStock(product.getStock() - cart.getAmoun());
                        productsService.save(product); // Actualizar stock del producto
                        cart.setBilled(true);
                        cartsService.save(cart); // Marcar el carrito como facturado
                    }
                }
            }

            Invoice invoice = new Invoice();
            invoice.setClient(client.get());
            invoice.setTotal(total);
            invoice.setDateTime(now);

            Invoice savedInvoice = invoicesService.save(invoice);
            return ResponseEntity.ok(savedInvoice);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/{clientId}")
    public ResponseEntity<Invoice> getLastInvoice(@PathVariable Long clientId) {
        try {
            List<Invoice> invoices = invoicesService.readAll().stream()
                    .filter(invoice -> invoice.getClient().getId().equals(clientId))
                    .sorted((i1, i2) -> i2.getDateTime().compareTo(i1.getDateTime())) // Ordenar por fecha descendente
                    .collect(Collectors.toList());
            if (!invoices.isEmpty()) {
                return ResponseEntity.ok(invoices.get(0)); // Retornar la factura más reciente
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e){
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
