package com.nttdata.quarkus.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CustomerRequest(@NotBlank String name, @NotBlank String lastname,
                              @NotBlank @Email String email, @NotBlank String clave) {
}
