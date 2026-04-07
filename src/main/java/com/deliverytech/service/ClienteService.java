package com.deliverytech.service;

import com.deliverytech.model.Cliente;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

@Cacheable("clientes")
public interface ClienteService {
    Cliente cadastrar(Cliente cliente);
    Optional<Cliente> buscarPorId(Long id);
    Page<Cliente> listarAtivos(Pageable pageable);
    Cliente atualizar(Long id, Cliente clienteAtualizado);
    void ativarDesativar(Long id);
}

