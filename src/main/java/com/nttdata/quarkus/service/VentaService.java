package com.nttdata.quarkus.service;

import com.nttdata.quarkus.dto.kafka.VentaDto;
import com.nttdata.quarkus.entity.Venta;
import com.nttdata.quarkus.kafka.VentaProducer;
import com.nttdata.quarkus.repository.VentaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class VentaService {


    @Inject
    VentaProducer ventaProducer;

    @Inject
    VentaRepository ventaRepository;

    public void sendVenta(VentaDto request){
        ventaProducer.sendVenta(request);
    }

    public void confirmingVenta(VentaDto responseKafka){
        Venta venta = Venta.builder()
                .saleId(responseKafka.saleId())
                .costumerId(responseKafka.costumerId())
                .productId(responseKafka.productId())
                .status(responseKafka.status())
                .build();
        ventaRepository.persist(venta);
    }
}
