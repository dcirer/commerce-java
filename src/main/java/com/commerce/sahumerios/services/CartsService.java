package com.commerce.sahumerios.services;

import com.commerce.sahumerios.entities.Cart;
import com.commerce.sahumerios.entities.Client;
import com.commerce.sahumerios.entities.Product;
import com.commerce.sahumerios.repositories.CartRepository;
import com.commerce.sahumerios.repositories.ClientRepository;
import com.commerce.sahumerios.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartsService {
    @Autowired private CartRepository repository;
    @Autowired private ClientRepository clientRepository;
    @Autowired private ProductRepository productRepository;

    //servicio que crea o modifica un carrito en tabla cart, utilizando el repositorio jpa.
    public Cart save (Cart cart){
        return repository.save(cart);
    }
    //servicio que lee todos los datos de la tabla cart, utilizando el repositorio jpa.
    public List<Cart> readAll(){
        return repository.findAll();
    }
    //servicio que lee un dato de la tabla cart busca el id, utilizando el repositorio jpa.
    public Optional<Cart> readOne(Long id){
        return repository.findById(id);
    }
    //servicio que elimina un dato de la tabla cart busca el id, utilizando el repositorio jpa.
    public Optional<Cart> destroyOne(Long id){
        Optional<Cart> cart = repository.findById(id);
        if (cart.isPresent()){
            repository.deleteById(id);
        }
        return cart;
    }
    //servicio que crea el carro, teniendo en cuenta el id producto seleccionado y el id cliente seleccionado y lo guarda en la tabla cart.
    //utilizando el repositorio jpa.
    public Optional<Cart> carts (Long clientId, Long cartId, Long productId){
        Optional<Cart>  cart = repository.findById(cartId);
        Optional<Client> client = clientRepository.findById(clientId);
        Optional<Product> product = productRepository.findById(productId);
        if (cart.isEmpty() || client.isEmpty() || product.isEmpty()){
            return cart;
        }
        Cart foundCart = cart.get();
        Client foundClient = client.get();
        Product foundProduct = product.get();

        foundCart.setClient(foundClient);
        foundCart.setProduct(foundProduct);

        repository.save(foundCart);
        return Optional.of(foundCart);
    }


}
