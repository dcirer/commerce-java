package com.commerce.sahumerios.controllers;

import com.commerce.sahumerios.entities.Cart;
import com.commerce.sahumerios.entities.Client;
import com.commerce.sahumerios.entities.Product;
import com.commerce.sahumerios.services.CartsService;
import com.commerce.sahumerios.services.ClientsService;
import com.commerce.sahumerios.services.ProductsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/carts")
public class CartController {

    @Autowired private CartsService cartsService;
    @Autowired private ClientsService clientsService;
    @Autowired private ProductsService productsService;


    //Metodo para agregar productos al carrito,
    // ademas verifica que no se agregue al carrito una cantidad que no hay en stock.
    @PostMapping("/{clientId}/{productId}")
    public ResponseEntity<Cart> addProduct(@PathVariable Long clientId, @PathVariable Long productId, @Valid @RequestBody Cart cart){
        try {
            Optional<Client> optionalClient = clientsService.readOne(clientId);
            Optional<Product> optionalProduct = productsService.readOne(productId);
            if (optionalClient.isPresent() && optionalProduct.isPresent()){
                Product foundProduct= optionalProduct.get();
                if (cart.getAmoun()> foundProduct.getStock()){
                    return ResponseEntity.badRequest().body(null);
                }
                cart.setClient(optionalClient.get());
                cart.setProduct(optionalProduct.get());
                Cart savedCart = cartsService.save(cart);
                return ResponseEntity.ok(savedCart);
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }catch (Exception e){
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
    //metodo que elimina un producto del carrito.
    @DeleteMapping("/{id}")
    public ResponseEntity<Cart> removeProduct(@PathVariable Long id){
        try {
            Optional<Cart> cart = cartsService.destroyOne(id);
            if (cart.isPresent()){
                return ResponseEntity.ok(cart.get());
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }catch (Exception e){
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
