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
@WithMockUser(authorities = "ROLE_CLIENTE")
public class ClienteControllerTest {

    @Autowired 
    MockMvc mockMvc;

    @Test
    void deveCriarClienteComSucesso() throws Exception {
        String json = "{\"nome\":\"José\",\"email\":\"jose@teste.com\"}";
        
        mockMvc.perform(post("/api/clientes")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(json))
            .andExpect(status().isCreated());
    }

    @Test
    void naoDeveCriarClienteComNomeEmBranco() throws Exception {
        String json = "{\"nome\":\"\",\"email\":\"jose@teste.com\"}";
        
        mockMvc.perform(post("/api/clientes")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(json))
            .andExpect(status().isBadRequest());
    }
    
    @Test
    void naoDeveCriarClienteComEmailEmBranco() throws Exception {
        String json = "{\"nome\":\"José\",\"email\":\"\"}";
        
        mockMvc.perform(post("/api/clientes")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(json))
            .andExpect(status().isBadRequest());
    }

    @Test
    void deveExcluirClienteComSucesso() throws Exception {
        String email = "jose@teste.com";
        
        mockMvc.perform(delete("/api/clientes/by-email/" + email))
            .andExpect(status().isNoContent());
    }
}
