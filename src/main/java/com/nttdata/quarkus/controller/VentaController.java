package com.nttdata.quarkus.controller;

import com.nttdata.quarkus.dto.kafka.VentaDto;
import com.nttdata.quarkus.service.VentaService;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;


@Path("/sales")
public class VentaController {

    @Inject
    VentaService ventaService;

    @POST
    public Response createSale(VentaDto request){
        ventaService.sendVenta(request);
        return Response.accepted().build();
    }
}
