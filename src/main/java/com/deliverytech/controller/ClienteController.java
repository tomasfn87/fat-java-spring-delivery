package com.deliverytech.controller;

import com.deliverytech.dto.request.ClienteRequest;
import com.deliverytech.dto.response.ClienteResponse;
import com.deliverytech.exception.EntityNotFoundException;
import com.deliverytech.model.Cliente;
import com.deliverytech.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Tag(
    name = "Clientes",
    description = "Endpoints para gerenciamento de clientes. Permite cadastrar, listar, buscar, atualizar e ativar/desativar clientes."
)
@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);
    private final ClienteService clienteService;

    @Operation(
        summary = "Cadastrar um novo cliente",
        description = "Permite que um novo cliente seja cadastrado. O endpoint é só para usuários com papel de ADMIN ou CLIENTE."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados de cadastro inválidos")
    })
    @PostMapping
    public ResponseEntity<ClienteResponse> cadastrar(@Valid @RequestBody ClienteRequest request) {
        logger.info("Cadastro de cliente iniciado: {}", request.getEmail());

        Cliente cliente = Cliente.builder()
            .nome(request.getNome())
            .email(request.getEmail())
            .ativo(true)
            .build();

        Cliente salvo = clienteService.cadastrar(cliente);
        logger.debug("Cliente salvo com ID {}", salvo.getId());

        // Retorna 201 Created com a localização do novo recurso no header
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(salvo.getId())
            .toUri();

        return ResponseEntity.created(location).body(new ClienteResponse(salvo.getId(), salvo.getNome(), salvo.getEmail(), salvo.getAtivo()));
    }

    @Operation(
        summary = "Listar todos os clientes ativos de forma paginada",
        description = "Permite que todos os clientes ativos sejam listados de forma paginada. O endpoint é só para usuários com papel de ADMIN."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de clientes ativos retornada com sucesso")
    })
    @GetMapping
    public Page<ClienteResponse> listar(Pageable pageable) {
        logger.info("Listando todos os clientes ativos de forma paginada");
        Page<Cliente> clientesPage = clienteService.listarAtivos(pageable);
        return clientesPage
                .map(c -> new ClienteResponse(c.getId(), c.getNome(), c.getEmail(), c.getAtivo()));
    }

    @Operation(
        summary = "Listar clientes ativos em um endpoint simplificado",
        description = "Permite que clientes ativos sejam listados em um endpoint simplificado. O endpoint é só para usuários com papel de ADMIN."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de clientes ativos retornada com sucesso")
    })
    @GetMapping("/all") // Mapeia a URL http://localhost:8080/api/clientes/all
    public Page<ClienteResponse> listarClientesNoEndpointSimples(Pageable pageable) {
        logger.info("Acessando o endpoint simplificado /api/clientes/all");
        Page<Cliente> clientesPage = clienteService.listarAtivos(pageable);
        return clientesPage
                .map(c -> new ClienteResponse(c.getId(), c.getNome(), c.getEmail(), c.getAtivo()));
    }

    @Operation(
        summary = "Buscar um cliente por ID",
        description = "Permite que um cliente seja buscado por seu ID. O endpoint é só para usuários com papel de ADMIN."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> buscar(@PathVariable Long id) {
        logger.info("Buscando cliente com ID: {}", id);
        return clienteService.buscarPorId(id)
                .map(c -> new ClienteResponse(c.getId(), c.getNome(), c.getEmail(), c.getAtivo()))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException("Cliente", id));
    }

    @Operation(
        summary = "Atualizar um cliente existente",
        description = "Permite que um cliente existente seja atualizado. O endpoint é só para usuários com papel de ADMIN ou CLIENTE."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> atualizar(@PathVariable Long id, @Valid @RequestBody ClienteRequest request) {
        logger.info("Atualizando cliente ID: {}", id);

        Cliente atualizado = Cliente.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .build();

        Cliente salvo = clienteService.atualizar(id, atualizado);

        return ResponseEntity.ok(new ClienteResponse(salvo.getId(), salvo.getNome(), salvo.getEmail(), salvo.getAtivo()));
    }

    @Operation(
        summary = "Ativar ou desativar um cliente",
        description = "Permite que um cliente seja ativado ou desativado. O endpoint é só para usuários com papel de ADMIN."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Status do cliente alterado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> ativarDesativar(@PathVariable Long id) {
        logger.info("Alterando status do cliente ID: {}", id);
        clienteService.ativarDesativar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Verificar status da API",
        description = "Permite verificar se a API está online. O endpoint é público e pode ser acessado por qualquer pessoa."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "API está online")
    })
    @GetMapping("/status")
    public ResponseEntity<String> status() {
        logger.debug("Status endpoint acessado");
        int cpuCores = Runtime.getRuntime().availableProcessors();
        logger.info("CPU cores disponíveis: {}", cpuCores);
        return ResponseEntity.ok("API está online");
    }
}
