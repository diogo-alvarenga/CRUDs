package com.example.crud.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.crud.business.ProdutoService;
import com.example.crud.structure.entity.Produto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/produto")
@RequiredArgsConstructor
public class ProdutoController {

	private final ProdutoService service;
	
	@GetMapping
	public ResponseEntity<Produto> buscarProduto(@RequestParam Integer id) {
		return ResponseEntity.ok(service.buscarProduto(id));
	}
	
	@PostMapping
	public ResponseEntity<Produto> adicionarProduto(@RequestBody Produto produto){
		return ResponseEntity.ok(service.adicionarProduto(produto));
	}
	
	@DeleteMapping
	public ResponseEntity<Void> deletarProduto(@RequestParam Integer id){
		service.deletarProduto(id);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping
	public ResponseEntity<Produto> atualizarProduto(@RequestBody Produto produto,@RequestParam Integer id){
		return ResponseEntity.ok(service.atualizarProduto(produto, id));
	}
}
