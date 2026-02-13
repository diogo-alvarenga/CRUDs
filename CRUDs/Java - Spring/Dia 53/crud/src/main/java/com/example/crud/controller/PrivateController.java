package com.example.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/usuario/private")
@Validated
public class PrivateController {

	@Autowired
	private PrivateService service;
	
	public ResponseEntity<String> acessar(@RequestHeader(value = "Authorization", required = true) @NotBlank String authHeader){
		return service.acessar(authHeader);
	}
}
