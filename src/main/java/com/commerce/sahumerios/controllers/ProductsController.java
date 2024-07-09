package com.commerce.sahumerios.controllers;

import com.commerce.sahumerios.entities.Product;
import com.commerce.sahumerios.services.ProductsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/products")
public class ProductsController {
    @Autowired private ProductsService service;

    @PostMapping
    public ResponseEntity<Product>create(@Valid @RequestBody Product product){
        try {
            Product newProduct = service.save(product);
            return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping
    public ResponseEntity<List<Product>> readAll(){
        try{
            List<Product> products = service.readAll();
            return ResponseEntity.ok(products);
        }catch (Exception e){
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> read(@PathVariable Long id){
        try{
            Optional<Product> product = service.readOne(id);
            if (product.isPresent()){
                return ResponseEntity.ok(product.get());
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }catch (Exception e){
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PutMapping ("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @Valid @RequestBody Product data){
        try{
            Optional<Product> optionalProduct = service.readOne(id);
            if (optionalProduct.isPresent()){
                Product product = optionalProduct.get();
                if (data.getDescription() != null){
                    product.setDescription(data.getDescription());
                }
                if (data.getStock() != null){
                    product.setStock(data.getStock());
                }

                product.setPrice(data.getPrice());
                service.save(product);
                return ResponseEntity.ok(product);
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }catch (Exception e){
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> destroy(@PathVariable Long id){
        try {
            Optional<Product> product = service.destroyOne(id);
            if (product.isPresent()){
                return ResponseEntity.ok(product.get());
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }catch (Exception e){
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
