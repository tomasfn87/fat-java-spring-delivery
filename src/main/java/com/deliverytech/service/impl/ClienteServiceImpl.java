package com.deliverytech.service.impl;

import com.deliverytech.model.Cliente;
import com.deliverytech.repository.ClienteRepository;
import com.deliverytech.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    @Override
    public Cliente cadastrar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    @Override
    public Page<Cliente> listarAtivos(Pageable pageable) {
        return clienteRepository.findByAtivoTrue(pageable);
    }

    @Override
    public Cliente atualizar(Long id, Cliente atualizado) {
        return clienteRepository.findById(id)
                .map(c -> {
                    c.setNome(atualizado.getNome());
                    return clienteRepository.save(c);
                }).orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    @Override
    public void ativarDesativar(Long id) {
        clienteRepository.findById(id).ifPresent(c -> {
            c.setAtivo(!c.getAtivo());
            clienteRepository.save(c);
        });
    }
 private void simulateDelay() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
