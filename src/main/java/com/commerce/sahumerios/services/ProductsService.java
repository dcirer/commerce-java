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

    public Product save (Product product){
        return repository.save(product);
    }

    public List<Product> readAll(){
        return repository.findAll();
    }

    public Optional<Product> readOne(Long id){
        return repository.findById(id);
    }

    public Optional<Product> destroyOne(Long id){
        Optional<Product> product = repository.findById(id);
        if (product.isPresent()){
            repository.deleteById(id);
        }
        return product;
    }

}
