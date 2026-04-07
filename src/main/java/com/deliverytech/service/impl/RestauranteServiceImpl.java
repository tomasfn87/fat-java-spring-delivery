package com.deliverytech.service.impl;

import com.deliverytech.model.Restaurante;
import com.deliverytech.repository.RestauranteRepository;
import com.deliverytech.service.RestauranteService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestauranteServiceImpl implements RestauranteService {

    private final RestauranteRepository restauranteRepository;

    @Override
    public Restaurante cadastrar(Restaurante restaurante) {
        return restauranteRepository.save(restaurante);
    }

    @Override
    public Optional<Restaurante> buscarPorId(Long id) {
        return restauranteRepository.findById(id);
    }

    @Override
    public Page<Restaurante> listarTodos(Pageable pageable) {
        return restauranteRepository.findAll(pageable);
    }

    @Override
    public Page<Restaurante> buscarPorCategoria(String categoria, Pageable pageable) {
        return restauranteRepository.findByCategoria(categoria, pageable);
    }

    @Override
    public Restaurante atualizar(Long id, Restaurante atualizado) {
        return restauranteRepository.findById(id)
            .map(r -> {
                r.setNome(atualizado.getNome());
                r.setTelefone(atualizado.getTelefone());
                r.setCategoria(atualizado.getCategoria());
                r.setTaxaEntrega(atualizado.getTaxaEntrega());
                r.setTempoEntregaMinutos(atualizado.getTempoEntregaMinutos());
                return restauranteRepository.save(r);
            }).orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));
    }
}
