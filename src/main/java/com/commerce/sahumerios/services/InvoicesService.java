package com.commerce.sahumerios.services;

import com.commerce.sahumerios.entities.Client;
import com.commerce.sahumerios.entities.Invoice;
import com.commerce.sahumerios.entities.Product;
import com.commerce.sahumerios.repositories.ClientRepository;
import com.commerce.sahumerios.repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoicesService {
    @Autowired private InvoiceRepository repository;
    @Autowired private ClientRepository clientRepository;

    public Invoice save (Invoice invoice){
        return repository.save(invoice);
    }

    public List<Invoice> readAll(){
        return repository.findAll();
    }

    public Optional<Invoice> readOne(Long id){
        return repository.findById(id);
    }

    public Optional<Invoice> destroyOne(Long id){
        Optional<Invoice> invoice = repository.findById(id);
        if (invoice.isPresent()){
            repository.deleteById(id);
        }
        return invoice;
    }

    public Optional<Invoice> invoices(Long invoiceId, Long clientId){
        Optional<Invoice> invoice = repository.findById(invoiceId);
        Optional<Client> client = clientRepository.findById(clientId);
        if (invoice.isEmpty() || client.isEmpty()){
            return invoice;
        }
        Invoice foundInvoice = invoice.get();
        Client foundClient = client.get();
        foundInvoice.setClient(foundClient);
        repository.save(foundInvoice);
        return invoice;
    }
}
