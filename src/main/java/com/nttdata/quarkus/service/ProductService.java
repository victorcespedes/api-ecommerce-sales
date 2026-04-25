package com.nttdata.quarkus.service;

import com.nttdata.quarkus.client.ProductClient;
import com.nttdata.quarkus.dto.external.ProductResponse;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.Set;

@ApplicationScoped
public class ProductService {

    @RestClient
    ProductClient productClient;

    public Set<ProductResponse> findAllProducts(){
        return productClient.listAllProduct();
    }



}
