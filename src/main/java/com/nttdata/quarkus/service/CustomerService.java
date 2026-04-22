package com.nttdata.quarkus.service;

import com.nttdata.quarkus.dto.CustomerRequest;
import com.nttdata.quarkus.dto.CustomerResponse;
import com.nttdata.quarkus.entity.Customer;
import com.nttdata.quarkus.repository.CustomerRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class CustomerService {

    @Inject
    CustomerRepository customerRepository;

    @Transactional
    public CustomerResponse create(CustomerRequest request){
        Customer customer = Customer.builder().name(request.name())
                .email(request.email())
                .lastname(request.lastname())
                .clave(request.clave()).build();
        customerRepository.persist(customer);

        return new CustomerResponse(customer.getId(), customer.getName(),
                customer.getLastname());

    }

    @Transactional
    public CustomerResponse update(Long id, CustomerRequest request){
        Customer customer = customerRepository.findById(id);
        if(customer == null)
            throw new NotFoundException("Customer not found.");
        customer.setName(request.name());
        customer.setLastname(request.lastname());
        customer.setEmail(request.email());
        customer.setClave(request.clave());
        customerRepository.persist(customer);

        return new CustomerResponse(id,request.name(),request.lastname());
    }

    @Transactional
    public void delete(Long id){
        Customer customer = customerRepository.findById(id);

        if(customer == null)
            throw  new NotFoundException("Customer not found.");

        customerRepository.delete(customer);

    }

    public List<CustomerResponse> findAll(){
        return customerRepository.findAll().stream()
                .map(cs -> new CustomerResponse(cs.getId(),
                        cs.getName(),cs.getLastname()))
                .toList();
    }

    public CustomerResponse findId(Long id){
        Customer customer = customerRepository.findById(id);

        if(customer == null)
            throw  new NotFoundException("Customer not found.");

        return new CustomerResponse(customer.getId(),customer.getName(),
                customer.getLastname());
    }


}
