package com.deliverytech.dto.request;

import com.deliverytech.model.Endereco;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoRequest {

    @NotNull(message = "O ID do cliente é obrigatório ")
    private Long clienteId;

    @NotNull(message = "O ID do restaurante é obrigatório ")
    private Long restauranteId;

    @NotNull(message = "O endereço de entrega é obrigatório")
    @Valid // <-- Importante: Valida os campos dentro do objeto Endereco
    private Endereco enderecoEntrega;

    @NotEmpty(message = "O pedido deve ter pelo menos um item")
    @Valid // <-- Importante: Valida cada ItemPedidoRequest dentro da lista
    private List<ItemPedidoRequest> itens;
}
