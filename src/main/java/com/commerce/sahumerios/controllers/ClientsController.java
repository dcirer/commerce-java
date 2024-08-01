package com.commerce.sahumerios.controllers;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.commerce.sahumerios.entities.Client;
import com.commerce.sahumerios.services.ClientsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/clients")
@Tag(name= "Client routes", description = "Client CRUD")
public class ClientsController {
    @Autowired private ClientsService service;

    @PostMapping
    @Operation(summary ="Metodo para crear un cliente",description = "Necesita que le pasen un objeto con los datos del cliente y devuelve el cliente creado")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "Cliente creado correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
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
    @Operation(summary ="Metodo para obtener todos los clientes",description = "Detalla una lista con todos los clientes.")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Lista de todos los clientes obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
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
    @Operation(summary ="Busca y visualiza un cliente",description = "Precisa un idclient para buscar.")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró el cliente con el ID proporcionado"),
            @ApiResponse(responseCode = "500", description = "El servidor se cayo por problemas diversos")
    })
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
    @Operation(summary ="Metodo para actualizar un cliente",description = "Precisa un idclient para buscar y modificar")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Cliente actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró el cliente con el ID proporcionado"),
            @ApiResponse(responseCode = "500", description = "El servidor se cayo por problemas diversos")
    })
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
    @Operation(summary ="Metodo para eliminar un cliente",description = "Precisa un idclient para buscar y eliminar")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Cliente eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró el cliente con el ID proporcionado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
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
