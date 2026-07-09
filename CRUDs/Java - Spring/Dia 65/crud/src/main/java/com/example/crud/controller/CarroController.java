package com.example.crud.controller;

import com.example.crud.business.CarroService;
import com.example.crud.dto.CarroAtualizacaoDTO;
import com.example.crud.dto.CarroEntradaDTO;
import com.example.crud.infrastructure.entity.Carro;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carro")
@RequiredArgsConstructor
public class CarroController {

    private final CarroService service;

    @PostMapping
    public ResponseEntity<Carro> adicionarCarro(@Valid @RequestBody CarroEntradaDTO entrada){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.adicionarCarro(entrada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCarro(@PathVariable Long id){
        service.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Carro> atualizarCarro(@Valid @RequestBody CarroAtualizacaoDTO entrada, @PathVariable Long id){
        return ResponseEntity.ok(service.atualizarCarro(entrada, id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carro> buscarCarro(@PathVariable Long id){
        return ResponseEntity.ok(service.buscarCarroPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<Carro>> listarCarro(){
        return ResponseEntity.ok(service.listarTodos());
    }
}
