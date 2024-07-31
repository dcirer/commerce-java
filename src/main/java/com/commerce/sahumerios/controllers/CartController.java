package com.commerce.sahumerios.controllers;

import com.commerce.sahumerios.entities.Cart;
import com.commerce.sahumerios.entities.Client;
import com.commerce.sahumerios.entities.Product;
import com.commerce.sahumerios.services.CartsService;
import com.commerce.sahumerios.services.ClientsService;
import com.commerce.sahumerios.services.ProductsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/carts")
@Tag(name= "Cart routes", description = "Cart CRUD")
public class CartController {

    @Autowired private CartsService cartsService;
    @Autowired private ClientsService clientsService;
    @Autowired private ProductsService productsService;


    @PostMapping("/{clientId}/{productId}/{quantity}")
    @Operation(summary ="Metodo para agregar productos al carrito",description = "ademas verifica que no se agregue al carrito una cantidad que no hay en stock")
    public ResponseEntity<Cart> addProduct(@PathVariable Long clientId, @PathVariable Long productId, @PathVariable Integer quantity){
        try {
            Optional<Client> optionalClient = clientsService.readOne(clientId);
            Optional<Product> optionalProduct = productsService.readOne(productId);
            if (optionalClient.isPresent() && optionalProduct.isPresent()){
                Product foundProduct = optionalProduct.get();
                if (quantity > foundProduct.getStock()){
                    return ResponseEntity.badRequest().body(null);
                }
                Cart cart = new Cart();
                cart.setClient(optionalClient.get());
                cart.setProduct(foundProduct);
                cart.setAmoun(quantity);
                cart.setPrice(foundProduct.getPrice());
                cart.setBilled(false);
                Cart savedCart = cartsService.save(cart);
                return ResponseEntity.ok(savedCart);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e){
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{clientId}/{productId}")
    @Operation(summary ="Metodo que elimina un producto del carrito",description = "Precisa que se le pase un idcliente y un idproduct")
    public ResponseEntity<Cart> removeProduct(@PathVariable Long clientId, @PathVariable Long productId) {
        try {
            List<Cart> carts = cartsService.readAll();
            for (Cart cart : carts) {
                if (cart.getClient().getId().equals(clientId) && cart.getProduct().getId().equals(productId) && !cart.isBilled()) {
                    cartsService.destroyOne(cart.getId());
                    return ResponseEntity.ok(cart);
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{clientId}")
    @Operation(summary ="Metodo que obitiene la lista de productos de un carrito",description = "Precisa el id del cliente, ademas verificar si esta facturado o no")
    public ResponseEntity<List<Cart>> getCarts(@PathVariable Long clientId){
        try {
            List<Cart> carts = cartsService.readAll().stream()
                    .filter(cart -> cart.getClient().getId().equals(clientId) && !cart.isBilled())
                    .collect(Collectors.toList());
            return ResponseEntity.ok(carts);
        } catch (Exception e){
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
