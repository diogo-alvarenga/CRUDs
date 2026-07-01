package com.example.crud.controller;

import com.example.crud.assembler.Assembler;
import com.example.crud.business.UsuarioService;
import com.example.crud.dto.AtualizacaoDTO;
import com.example.crud.dto.EntradaDTO;
import com.example.crud.dto.SaidaDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;
    private final Assembler assembler;

    @PostMapping
    public ResponseEntity<SaidaDTO> adicionarUsuario(@RequestBody @Valid EntradaDTO entrada){
        SaidaDTO saida = service.adicionarUsuario(entrada);

        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.addHateoas(saida));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id){
        service.excluirUsuario(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaidaDTO> atualizarUsuario(@PathVariable Long id, @RequestBody @Valid AtualizacaoDTO entradaAtl){
        SaidaDTO saidaAtl = service.atualizarUsuario(entradaAtl, id);

        return ResponseEntity.ok(assembler.addHateoas(saidaAtl));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaidaDTO> buscarUsuario(@PathVariable Long id){
        return ResponseEntity.ok(assembler.addHateoas(service.buscarUsuario(id)));
    }

    @GetMapping
    public ResponseEntity<List<SaidaDTO>> listarTodos(){
        return ResponseEntity.ok(service.listarTodos());
    }
}
