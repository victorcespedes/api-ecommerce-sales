package com.nttdata.quarkus.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Table(name = "nttt_venta")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Venta {

    @Id
    @GeneratedValue
    Long id;

    @NotBlank
    String saleId;

    @NotBlank
    String costumerId;

    @NotBlank
    @Email
    String productId;

    @NotBlank
    String status;
}
