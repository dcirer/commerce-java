package com.commerce.sahumerios.services;

import com.commerce.sahumerios.entities.Cart;
import com.commerce.sahumerios.entities.Client;
import com.commerce.sahumerios.repositories.CartRepository;
import com.commerce.sahumerios.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientsService {
    @Autowired private ClientRepository repository;
    @Autowired private CartRepository cartRepository;

    public Client save (Client client){
        return repository.save(client);
    }

    public List<Client> readAll(){
        return repository.findAll();
    }

    public Optional<Client> readOne(Long id){
        return repository.findById(id);
    }

    public Optional<Client> destroyOne(Long id){
        Optional<Client> client = repository.findById(id);
        if (client.isPresent()){
            repository.deleteById(id);
        }
        return client;
    }



}
