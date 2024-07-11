package com.commerce.sahumerios.services;

import com.commerce.sahumerios.entities.Cart;
import com.commerce.sahumerios.entities.Product;
import com.commerce.sahumerios.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductsService {
    @Autowired private ProductRepository repository;

    //servicio que crea o modifica un producto en tabla product, utilizando el repositorio jpa.
    public Product save (Product product){
        return repository.save(product);
    }
    //servicio que lee todos los datos de la tabla product, utilizando el repositorio jpa.
    public List<Product> readAll(){
        return repository.findAll();
    }
    // servicio que lee un dato de la tabla product segun el id, utilizando el repositorio jpa.
    public Optional<Product> readOne(Long id){
        return repository.findById(id);
    }
    //servicio que elimina un dato de la tabla product, utilizando el repositorio jpa.
    public Optional<Product> destroyOne(Long id){
        Optional<Product> product = repository.findById(id);
        if (product.isPresent()){
            repository.deleteById(id);
        }
        return product;
    }

}
