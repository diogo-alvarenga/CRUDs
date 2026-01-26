package com.example.crud.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntradaDTO {

	@NotBlank
	private String nome;
	
	@Email
	@NotBlank
	private String email;
	
	@NotBlank
	private String senha;
}
