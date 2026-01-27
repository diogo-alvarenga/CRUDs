package com.example.crud.DTO;

import org.springframework.hateoas.RepresentationModel;

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
public class SaidaDTO extends RepresentationModel<SaidaDTO>{

	private String nome;
	private String email;
	private Long id;
}
