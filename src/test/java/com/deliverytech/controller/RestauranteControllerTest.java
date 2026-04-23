package com.deliverytech.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(authorities = "ROLE_ADMIN")
public class RestauranteControllerTest {

    @Autowired 
    MockMvc mockMvc;

    @Test
    void deveCriarRestauranteComSucesso() throws Exception {
        String json = "{\"nome\":\"Pizzaria da Galera\",\"categoria\":\"pizzaria\",\"telefone\":\"(19)3541-7789\",\"taxaEntrega\":10.0,\"tempoEntregaMinutos\":50}";
        
        mockMvc.perform(post("/api/restaurantes")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(json))
            .andExpect(status().isCreated());
    }

    @Test
    void naoDeveCriarRestauranteComNomeEmBranco() throws Exception {
        String json = "{\"nome\":\"\",\"categoria\":\"pizzaria\",\"telefone\":\"(19)3541-7789\",\"taxaEntrega\":10.0,\"tempoEntregaMinutos\":50}";
        
        mockMvc.perform(post("/api/restaurantes")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(json))
            .andExpect(status().isBadRequest());
    }

    @Test
    void deveExcluirRestauranteComSucesso() throws Exception {
        String nome = "Pizzaria da Galera";
        
        mockMvc.perform(delete("/api/restaurantes/by-nome/" + nome))
            .andExpect(status().isNoContent());
    }
}
