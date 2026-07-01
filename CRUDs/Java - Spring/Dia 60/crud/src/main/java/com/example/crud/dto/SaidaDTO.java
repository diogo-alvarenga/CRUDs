package com.example.crud.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaidaDTO extends RepresentationModel<SaidaDTO> {

    private Long id;
    private String nome;
    private String email;
}
