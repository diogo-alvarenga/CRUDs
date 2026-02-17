package com.example.crud.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.crud.business.PrivateService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("usuario/private")
public class PrivateController {

	private final PrivateService service;
	
	@GetMapping
	public ResponseEntity<String> acessar(@RequestHeader(value = "Authorization", required = true) String authHeader){
		return ResponseEntity.ok(service.acessar(authHeader));
	}
}