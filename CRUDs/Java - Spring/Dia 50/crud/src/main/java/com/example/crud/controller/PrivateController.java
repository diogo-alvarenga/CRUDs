package com.example.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.crud.business.PrivateService;

import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/usuario/private")
@Validated
public class PrivateController {
	
	@Autowired
	private PrivateService service;

	@GetMapping
	public ResponseEntity<String> verificarToken(@RequestHeader(value = "Authorization", required = true ) @NotBlank String authHeader){
		return ResponseEntity.ok(service.acessar(authHeader));
	}
}
