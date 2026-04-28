package com.nttdata.quarkus.dto.kafka;

public record VentaDto(String saleId,
                       String costumerId,
                       String productId,
                       String status) {

}
