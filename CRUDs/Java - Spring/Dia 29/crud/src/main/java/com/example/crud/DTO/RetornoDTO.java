package com.example.crud.DTO;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RetornoDTO extends RepresentationModel<RetornoDTO>{
	
	private Long id;
	private String nome;
	private String email;
	
}
