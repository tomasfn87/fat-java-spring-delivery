package com.deliverytech.repository;

import com.deliverytech.model.Restaurante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
    Page<Restaurante> findByCategoria(String categoria, Pageable pageable);
    Page<Restaurante> findByAtivoTrue(Pageable pageable);
}
