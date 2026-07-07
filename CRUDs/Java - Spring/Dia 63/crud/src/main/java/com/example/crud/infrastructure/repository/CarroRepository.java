package com.example.crud.infrastructure.repository;

import com.example.crud.infrastructure.entity.Carro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarroRepository extends JpaRepository<Carro, Long>{
    Optional<Carro> findByPlaca(String placa);
}
