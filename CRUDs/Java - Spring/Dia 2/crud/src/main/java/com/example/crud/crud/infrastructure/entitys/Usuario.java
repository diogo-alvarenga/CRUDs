package com.example.crud.crud.infrastructure.entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor //gera automaticamente um construtor com um parâmetro para cada campo da classe
@NoArgsConstructor //gera um construtor sem nenhum parâmetro.
@Builder //age como um construtor, voce envia informaçoes a ele
@Table(name = "usuario")
@Entity
public class Usuario {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "email", unique = true)
	private String email;
	
	
	@Column(name = "nome")
	private String nome;
}
