package com.example.crud.structure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.crud.structure.entity.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer>{

}
