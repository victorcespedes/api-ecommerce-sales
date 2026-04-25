package com.nttdata.quarkus.client;

import com.nttdata.quarkus.dto.external.ProductResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.Set;


@RegisterRestClient(configKey = "api-products")
public interface ProductClient {

    @GET
    @Path("/products")
    Set<ProductResponse> listAllProduct();
}
