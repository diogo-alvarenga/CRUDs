package com.example.crud.dto;

import com.example.crud.infrastructure.entity.Carro;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaidaUsuarioDTO extends RepresentationModel<SaidaUsuarioDTO> {
    private Long id;
    private String nome;
    private String email;
    private List<Carro> listaCarros;
}
