package com.commerce.sahumerios.controllers;

import com.commerce.sahumerios.entities.Product;
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

@RestController
@RequestMapping("api/v1/products")
@Tag(name= "Product routes", description = "Product CRUD")
public class ProductsController {
    @Autowired private ProductsService service;

    @PostMapping
    @Operation(summary ="Metodo para crear un producto",description = "Necesita que le pasen un objeto con los datos del producto y devuelve el producto creado")
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
    @Operation(summary ="Metodo para visualizar la lista de productos",description = "")
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
    @Operation(summary ="Metodo buscar y visualizar un producto",description = "Precisa un idproduct para buscar en la lista y visualizar.")
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
    @Operation(summary ="Metodo para modificar un producto",description = "Precisa un idproduct para buscar y modificar un producto")
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
    @Operation(summary ="Metodo para eliminar un producto",description = "Precisa un idproduct para buscar y eliminar un producto")
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
