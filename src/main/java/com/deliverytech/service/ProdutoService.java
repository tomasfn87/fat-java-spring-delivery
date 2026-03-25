package com.deliverytech.service;

import com.deliverytech.model.Produto;

import java.util.List;
import java.util.Optional;

public interface ProdutoService {
    Produto cadastrar(Produto produto);
    Optional<Produto> buscarPorId(Long id);
    List<Produto> buscarPorRestaurante(Long restauranteId);
    Produto atualizar(Long id, Produto produtoAtualizado);
    void alterarDisponibilidade(Long id, boolean disponivel);
}
