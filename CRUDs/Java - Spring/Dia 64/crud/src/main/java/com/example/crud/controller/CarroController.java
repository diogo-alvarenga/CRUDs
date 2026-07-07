package com.example.crud.controller;

import com.example.crud.business.CarroService;
import com.example.crud.dto.CarroAtualizacaoDTO;
import com.example.crud.dto.CarroEntradaDTO;
import com.example.crud.ifrastructure.entity.Carro;
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

    @GetMapping("/{id}")
    public ResponseEntity<Carro> buscarCarro(@PathVariable Long id){
        return ResponseEntity.ok(service.buscarCarro(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Carro> deletarCarro(@PathVariable Long id){
        service.deletarCarro(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Carro> atualizarCarro(@PathVariable Long id, @Valid @RequestBody CarroAtualizacaoDTO entrada){
        return ResponseEntity.ok(service.atualizarCarro(id, entrada));

    }

    @GetMapping
    public ResponseEntity<List<Carro>> listarCarros(){
        return ResponseEntity.ok(service.listarCarros());
    }
}