package com.deliverytech.controller;

import com.deliverytech.dto.request.ProdutoRequest;
import com.deliverytech.dto.response.ProdutoResponse;
import com.deliverytech.exception.EntityNotFoundException;
import com.deliverytech.model.Produto;
import com.deliverytech.model.Restaurante;
import com.deliverytech.service.ProdutoService;
import com.deliverytech.service.RestauranteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Tag(
    name = "Produtos",
    description = "Endpoints para gerenciamento de produtos. Permite cadastrar, listar, buscar, atualizar e ativar/desativar produtos."
)
@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;
    private final RestauranteService restauranteService;

    @PostMapping
    @Operation(
        summary = "Cadastrar um novo produto",
        description = "Permite que um novo produto seja cadastrado. O endpoint é só para usuários com papel de ADMIN."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Produto cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados do produto inválidos"),
        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    public ResponseEntity<ProdutoResponse> cadastrar(@Valid @RequestBody ProdutoRequest request) {
        Restaurante restaurante = restauranteService.buscarPorId(request.getRestauranteId())
                .orElseThrow(() -> new EntityNotFoundException("Restaurante", request.getRestauranteId()));

        Produto produto = Produto.builder()
                .nome(request.getNome())
                .categoria(request.getCategoria())
                .descricao(request.getDescricao())
                .preco(request.getPreco())
                .disponivel(true)
                .restaurante(restaurante)
                .build();

        Produto salvo = produtoService.cadastrar(produto);

        // Retorna 201 Created com a localização do novo recurso
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(salvo.getId())
                .toUri();

        return ResponseEntity.created(location).body(new ProdutoResponse(
                salvo.getId(), salvo.getNome(), salvo.getCategoria(), salvo.getDescricao(),
                salvo.getPreco(), salvo.getDisponivel()));
    }

    @GetMapping("/restaurante/{restauranteId}")
    @Operation(
        summary = "Listar produtos de um restaurante",
        description = "Permite que os produtos de um restaurante sejam listados. O endpoint é só para usuários com papel de ADMIN."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Produtos retornados com sucesso"),
        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    public List<ProdutoResponse> listarPorRestaurante(@PathVariable Long restauranteId) {
        // Valida se o restaurante existe antes de listar os produtos
        if (restauranteService.buscarPorId(restauranteId).isEmpty()) {
            throw new EntityNotFoundException("Restaurante", restauranteId);
        }
        
        return produtoService.buscarPorRestaurante(restauranteId).stream()
                .map(p -> new ProdutoResponse(p.getId(), p.getNome(), p.getCategoria(), p.getDescricao(), p.getPreco(), p.getDisponivel()))
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Atualizar um produto por ID",
        description = "Permite que um produto seja atualizado por seu ID. O endpoint é só para usuários com papel de ADMIN."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<ProdutoResponse> atualizar(@PathVariable Long id, @Valid @RequestBody ProdutoRequest request) {
        Produto atualizado = Produto.builder()
                .nome(request.getNome())
                .categoria(request.getCategoria())
                .descricao(request.getDescricao())
                .preco(request.getPreco())
                .build();
        Produto salvo = produtoService.atualizar(id, atualizado);
        return ResponseEntity.ok(new ProdutoResponse(salvo.getId(), salvo.getNome(), salvo.getCategoria(), salvo.getDescricao(), salvo.getPreco(), salvo.getDisponivel()));
    }

    @PatchMapping("/{id}/disponibilidade")
    @Operation(
        summary = "Alterar disponibilidade de um produto",
        description = "Permite que a disponibilidade de um produto seja alterada. O endpoint é só para usuários com papel de ADMIN."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Disponibilidade do produto alterada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<Void> alterarDisponibilidade(@PathVariable Long id, @RequestParam boolean disponivel) {
        produtoService.alterarDisponibilidade(id, disponivel);
        return ResponseEntity.noContent().build();
    }
}
