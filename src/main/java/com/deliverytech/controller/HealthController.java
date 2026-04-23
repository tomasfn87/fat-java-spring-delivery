package com.deliverytech.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.Map;

@Tag(
    name = "Saúde",
    description = "Endpoints para verificar o status da API. Permite verificar se a API está online."
)
@RestController
public class HealthController {

    @GetMapping("/health")
    @Operation(
        summary = "Verificar status da API",
        description = "Permite verificar se a API está online. O endpoint é público e pode ser acessado por qualquer pessoa."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "API está online com informações de status")
    })
    public Map<String, String> health() {
        return Map.of(
            "status", "UP",
            "timestamp", LocalDateTime.now().toString(),
            "service", "Delivery API",
            "javaVersion", System.getProperty("java.version")
        );
    }

    @GetMapping("/info")
    @Operation(
        summary = "Verificar status da API (endpoint alternativo)",
        description = "Permite verificar se a API está online usando um endpoint alternativo. O endpoint é público e pode ser acessado por qualquer pessoa."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Informações de status da API retornadas com sucesso")
    })
    public AppInfo info() {
        return new AppInfo(
            "Delivery Tech API",
            "1.0.0",
            "Tomás Foch Nalle",
            "JDK 25.0.2",
            "Spring Boot 3.2.x"
        );
    }

    // Record para demonstrar recurso do Java 14+ (disponível no JDK 21)
    public record AppInfo(
        String application,
        String version,
        String developer,
        String javaVersion,
        String framework
    ) {}
}