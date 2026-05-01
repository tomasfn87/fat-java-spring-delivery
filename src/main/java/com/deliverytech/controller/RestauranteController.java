package com.deliverytech.controller;

import com.deliverytech.dto.request.RestauranteRequest;
import com.deliverytech.dto.response.RestauranteResponse;
import com.deliverytech.exception.EntityNotFoundException;
import com.deliverytech.model.Restaurante;
import com.deliverytech.service.RestauranteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Tag(
    name = "Restaurantes",
    description = "Endpoints para gerenciamento de restaurantes. Permite cadastrar, listar, buscar, atualizar e ativar/desativar restaurantes."
)
@RestController
@RequestMapping("/api/restaurantes")
@RequiredArgsConstructor
public class RestauranteController {

    private static final Logger logger = LoggerFactory.getLogger(RestauranteController.class);
    private final RestauranteService restauranteService;

    @PostMapping
    @Operation(
        summary = "Cadastrar um novo restaurante",
        description = "Permite que um novo restaurante seja cadastrado. O endpoint é só para usuários com papel de ADMIN."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Restaurante cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados do restaurante inválidos")
    })
    public ResponseEntity<RestauranteResponse> cadastrar(@Valid @RequestBody RestauranteRequest request) {
        Restaurante restaurante = Restaurante.builder()
                .nome(request.getNome())
                .telefone(request.getTelefone())
                .categoria(request.getCategoria())
                .taxaEntrega(request.getTaxaEntrega())
                .tempoEntregaMinutos(request.getTempoEntregaMinutos())
                .ativo(true)
                .build();
        Restaurante salvo = restauranteService.cadastrar(restaurante);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(salvo.getId())
                .toUri();

        return ResponseEntity.created(location).body(new RestauranteResponse(
                salvo.getId(), salvo.getNome(), salvo.getCategoria(), salvo.getTelefone(),
                salvo.getTaxaEntrega(), salvo.getTempoEntregaMinutos(), salvo.getAtivo()));
    }

    @GetMapping
    @Operation(
        summary = "Listar todos os restaurantes",
        description = "Permite que todos os restaurantes sejam listados. O endpoint é só para usuários com papel de ADMIN."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de restaurantes retornada com sucesso")
    })
    public Page<RestauranteResponse> listarTodos(Pageable pageable) {
        Page<Restaurante> restaurantesPage = restauranteService.listarTodos(pageable);
        return restaurantesPage.map(r -> new RestauranteResponse(
            r.getId(), r.getNome(), r.getCategoria(), r.getTelefone(),
            r.getTaxaEntrega(), r.getTempoEntregaMinutos(), r.getAtivo()));
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Buscar um restaurante por ID",
        description = "Permite que um restaurante seja buscado por seu ID. O endpoint é só para usuários com papel de ADMIN."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Restaurante encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    public ResponseEntity<RestauranteResponse> buscarPorId(@PathVariable Long id) {
        return restauranteService.buscarPorId(id)
                .map(r -> new RestauranteResponse(
                    r.getId(), r.getNome(), r.getCategoria(), r.getTelefone(), r.getTaxaEntrega(),
                    r.getTempoEntregaMinutos(), r.getAtivo()))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException("Restaurante", id));
    }

    @GetMapping("/categoria/{categoria}")
    @Operation(
        summary = "Buscar restaurantes por categoria",
        description = "Permite que restaurantes sejam buscados por categoria. O endpoint é só para usuários com papel de ADMIN."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Restaurantes retornados por categoria com sucesso")
    })
    public Page<RestauranteResponse> buscarPorCategoria(@PathVariable String categoria, Pageable pageable) {
        Page<Restaurante> restaurantesPage = restauranteService.buscarPorCategoria(categoria, pageable);
        return restaurantesPage
                .map(r -> new RestauranteResponse(
                    r.getId(), r.getNome(), r.getCategoria(), r.getTelefone(), r.getTaxaEntrega(),
                    r.getTempoEntregaMinutos(), r.getAtivo()));
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Atualizar um restaurante existente",
        description = "Permite que um restaurante existente seja atualizado. O endpoint é só para usuários com papel de ADMIN."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Restaurante atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    public ResponseEntity<RestauranteResponse> atualizar(@PathVariable Long id, @Valid @RequestBody RestauranteRequest request) {
        Restaurante atualizado = Restaurante.builder()
                .nome(request.getNome())
                .telefone(request.getTelefone())
                .categoria(request.getCategoria())
                .taxaEntrega(request.getTaxaEntrega())
                .tempoEntregaMinutos(request.getTempoEntregaMinutos())
                .build();
        Restaurante salvo = restauranteService.atualizar(id, atualizado);
        return ResponseEntity.ok(new RestauranteResponse(
            salvo.getId(), salvo.getNome(), salvo.getCategoria(), salvo.getTelefone(),
            salvo.getTaxaEntrega(), salvo.getTempoEntregaMinutos(), salvo.getAtivo()));
    }

    @DeleteMapping("/by-nome/{nome}")
    @Operation(
        summary = "Deletar um restaurante por nome",
        description = "Permite que um restaurante seja deletado pelo seu nome. O endpoint é só para usuários com papel de ADMIN."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Restaurante deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    public ResponseEntity<Void> deletarPorNome(@PathVariable String nome) {
        logger.info("Deletando restaurante com nome: {}", nome);
        restauranteService.deletarPorNome(nome);
        return ResponseEntity.noContent().build();
    }

    @CacheEvict(value = "restaurantes",
                allEntries = true)
    @GetMapping("/restaurantes/cache/limpar")
    @Operation(
        summary = "Limpar o cache de Restaurantes",
        description = "Limpa o cache de Restaurantes, exigindo que o banco de dados seja consultado novamente."
    )
    public ResponseEntity<Void> limparCache() {
        return ResponseEntity
                .noContent().build();
    }
}
