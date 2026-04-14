package com.deliverytech.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoRequest {

    @NotBlank(message = "O nome não pode estar em branco")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    private String nome;

    @NotBlank(message = "A categoria não pode estar em branco")
    private String categoria;

    @NotBlank(message = "A descrição não pode estar em branco")
    @Size(max = 500, message = "A descrição não pode exceder 500 caracteres")
    private String descricao;

    @NotNull(message = "O preço é obrigatório")
    @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero")
    @DecimalMax(value = "10000.0", message = "O preço não pode exceder R$10.000,00")
    private BigDecimal preco;

    @NotNull(message = "O ID do restaurante é obrigatório")
    private Long restauranteId;
}
