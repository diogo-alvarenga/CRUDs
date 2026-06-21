package com.example.crud.dto;

import com.example.crud.infrastructure.entity.Usuario;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaidaCarroDTO extends RepresentationModel<SaidaCarroDTO> {
    private Long id;
    private String modelo;
    private String fabricante;
    private Integer ano;
    private Float kmRodados;
    private Usuario usuario;
}
