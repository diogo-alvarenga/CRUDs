package com.example.crud.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EntradacomIdDTO {

	private Long id;
	private String email;
	private String nome;
	private String senha;
}
