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

    //servicio que crea o modifica un cliente en tabla client, utilizando el repositorio jpa.
    public Client save (Client client){
        return repository.save(client);
    }
    //servicio que lee todos los datos de la tabla cliente, utilizando el repositorio jpa.
    public List<Client> readAll(){
        return repository.findAll();
    }
    // servicio que lee un dato de la tabla clientes segun el id, utilizando el repositorio jpa.
    public Optional<Client> readOne(Long id){
        return repository.findById(id);
    }
    //servicio que elimina un dato de la tabla cliente, utilizando el repositorio jpa.
    public Optional<Client> destroyOne(Long id){
        Optional<Client> client = repository.findById(id);
        if (client.isPresent()){
            repository.deleteById(id);
        }
        return client;
    }



}
