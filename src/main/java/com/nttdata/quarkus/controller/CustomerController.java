package com.nttdata.quarkus.controller;

import com.nttdata.quarkus.dto.CustomerRequest;
import com.nttdata.quarkus.service.CustomerService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerController {

    @Inject
    CustomerService customerService;

    @POST
    public Response create(@Valid CustomerRequest request){
        return Response.status(Response.Status.CREATED)
                    .entity(customerService.create(request))
                    .build();
    }

    @GET
    public Response findListAll(){
        return Response.ok(customerService.findAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response findIdCustomer(@PathParam("id") Long id){
        return Response.ok(customerService.findId(id)).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id,@Valid CustomerRequest request){
        return Response.ok(customerService.update(id, request)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id){
        customerService.delete(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }


}
