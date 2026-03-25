package com.deliverytech.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestauranteRequest {

    @NotBlank
    private String nome;

    @NotBlank
    private String categoria;

    @NotBlank
    private String telefone;

    @DecimalMin("0.0")
    private BigDecimal taxaEntrega;

    @Min(10)
    @Max(120)
    private Integer tempoEntregaMinutos;
}
