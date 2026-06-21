package com.example.crud.controller;

import com.example.crud.business.CarroService;
import com.example.crud.dto.AtualizacaoCarroDTO;
import com.example.crud.dto.EntradaCarroDTO;
import com.example.crud.dto.SaidaCarroDTO;
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
    public ResponseEntity<SaidaCarroDTO> adicionarCarro(@Valid @RequestBody EntradaCarroDTO carro){

        return ResponseEntity.status(HttpStatus.CREATED) .body(service.salvarCarro(carro));
    }

    @DeleteMapping("/{id}")
    public void deletarCarro(@PathVariable Long id){
        service.deletarCarro(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaidaCarroDTO> atualizarCarro(@PathVariable Long id, @RequestBody AtualizacaoCarroDTO carro){
        return ResponseEntity.ok(service.atualizarCarro(id, carro));
    }

    @GetMapping("/{id}")
    public SaidaCarroDTO buscarCarro(@PathVariable Long id){
        return service.buscarCarro(id);
    }

    @GetMapping
    public List<SaidaCarroDTO> listarCarros(){
        return service.listarTodos();
    }
}
