package com.deliverytech.service;

import com.deliverytech.model.Restaurante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RestauranteService {
    Restaurante cadastrar(Restaurante restaurante);
    Optional<Restaurante> buscarPorId(Long id);
    Page<Restaurante> listarTodos(Pageable pageable);
    Page<Restaurante> buscarPorCategoria(String categoria, Pageable pageable);
    Restaurante atualizar(Long id, Restaurante restauranteAtualizado);
    void deletarPorNome(String nome);
}
