package com.deliverytech.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoRequest {

    @NotBlank
    private String nome;

    @NotBlank
    private String categoria;

    @NotBlank
    private String descricao;

    @DecimalMin("0.1")
    @DecimalMax("500.0")
    private BigDecimal preco;

    private Long restauranteId;
}
