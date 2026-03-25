package com.deliverytech.service;

import com.deliverytech.model.Cliente;
import org.springframework.cache.annotation.Cacheable;
import java.util.List;
import java.util.Optional;

@Cacheable("clientes")
public interface ClienteService {
    Cliente cadastrar(Cliente cliente);
    Optional<Cliente> buscarPorId(Long id);
    List<Cliente> listarAtivos();
    Cliente atualizar(Long id, Cliente clienteAtualizado);
    void ativarDesativar(Long id);
}

