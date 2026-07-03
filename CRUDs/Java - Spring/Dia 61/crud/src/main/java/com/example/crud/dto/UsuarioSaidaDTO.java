package com.example.crud.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioSaidaDTO extends RepresentationModel<UsuarioSaidaDTO> {

    private Long id;
    private String nome;
    private String email;

}
