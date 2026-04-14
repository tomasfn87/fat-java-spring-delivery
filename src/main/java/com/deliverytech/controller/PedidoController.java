package com.deliverytech.controller;

import com.deliverytech.dto.request.ItemPedidoRequest;
import com.deliverytech.dto.request.PedidoRequest;
import com.deliverytech.dto.response.ItemPedidoResponse;
import com.deliverytech.dto.response.PedidoResponse;
import com.deliverytech.exception.EntityNotFoundException;
import com.deliverytech.model.*;
import com.deliverytech.service.ClienteService;
import com.deliverytech.service.PedidoService;
import com.deliverytech.service.ProdutoService;
import com.deliverytech.service.RestauranteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;
    private final ClienteService clienteService;
    private final RestauranteService restauranteService;
    private final ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<PedidoResponse> criar(@Valid @RequestBody PedidoRequest request) {
        Cliente cliente = clienteService.buscarPorId(request.getClienteId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente", request.getClienteId()));
        Restaurante restaurante = restauranteService.buscarPorId(request.getRestauranteId())
                .orElseThrow(() -> new EntityNotFoundException("Restaurante", request.getRestauranteId()));

        List<ItemPedido> itens = request.getItens().stream().map(item -> {
            Produto produto = produtoService.buscarPorId(item.getProdutoId())
                    .orElseThrow(() -> new EntityNotFoundException("Produto", item.getProdutoId()));
            return ItemPedido.builder()
                    .produto(produto)
                    .quantidade(item.getQuantidade())
                    .precoUnitario(produto.getPreco())
                    .build();
        }).collect(Collectors.toList());

        BigDecimal total = itens.stream()
                .map(i -> i.getPrecoUnitario().multiply(BigDecimal.valueOf(i.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Pedido pedido = Pedido.builder()
                .cliente(cliente)
                .restaurante(restaurante)
                .status(StatusPedido.CRIADO)
                .total(total)
                .enderecoEntrega(request.getEnderecoEntrega())
                .itens(itens)
                .build();

        Pedido salvo = pedidoService.criar(pedido);

        List<ItemPedidoResponse> itensResp = salvo.getItens().stream()
                .map(i -> new ItemPedidoResponse(i.getProduto().getId(), i.getProduto().getNome(), i.getQuantidade(), i.getPrecoUnitario()))
                .collect(Collectors.toList());

        // Retorna 201 Created com a localização do novo recurso
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(salvo.getId())
                .toUri();

        PedidoResponse pedidoResponse = new PedidoResponse(
                salvo.getId(),
                cliente.getId(),
                restaurante.getId(),
                salvo.getEnderecoEntrega(),
                salvo.getTotal(),
                salvo.getStatus(),
                salvo.getDataPedido(),
                itensResp
        );

        return ResponseEntity.created(location).body(pedidoResponse);
    }
}
