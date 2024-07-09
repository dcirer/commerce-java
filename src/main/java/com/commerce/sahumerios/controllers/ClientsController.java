package com.commerce.sahumerios.controllers;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.commerce.sahumerios.entities.Client;
import com.commerce.sahumerios.services.ClientsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/clients")
public class ClientsController {
    @Autowired private ClientsService service;

    @PostMapping
    public ResponseEntity<Client> create(@Valid @RequestBody Client client){
        try {
            Client newClient = service.save(client);
            return new ResponseEntity<>(newClient, HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping
    public ResponseEntity<List<Client>> readAll (){
        try{
            List<Client> clients = service.readAll();
            return ResponseEntity.ok(clients);
        }catch (Exception e){
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> read(@PathVariable Long id){
        try{
            Optional<Client> client = service.readOne(id);
            if (client.isPresent()){
                return ResponseEntity.ok(client.get());
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }catch (Exception e){
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")

    public ResponseEntity<Client> update(@PathVariable Long id, @Valid @RequestBody Client data){
        try{
            Optional<Client> optionalClient =service.readOne(id);
            if (optionalClient.isPresent()){
                Client client = optionalClient.get();
                if (data.getName() != null){
                    client.setName(data.getName());
                }
                if (data.getLastname() != null){
                    client.setLastname(data.getLastname());
                }
                if (data.getDocnumber() !=null){
                    client.setDocnumber(data.getDocnumber());
                }
                service.save(client);
                return ResponseEntity.ok(client);
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }catch (Exception e){
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Client> destroy(@PathVariable Long id){
        try {
            Optional<Client> client = service.destroyOne(id);
            if (client.isPresent()){
                return ResponseEntity.ok(client.get());
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }catch (Exception e){
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
