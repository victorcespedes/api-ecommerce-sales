package com.nttdata.quarkus.dto;

public record CustomerResponse(Long id, String name,
                               String lastname) {
}
