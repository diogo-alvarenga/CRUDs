package com.example.crud.mapper;

import com.example.crud.dto.CarroAtualizacaoDTO;
import com.example.crud.dto.CarroEntradaDTO;
import com.example.crud.infrastructure.entity.Carro;
import com.example.crud.infrastructure.entity.Usuario;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CarroMapper {

    public Carro toEntity(CarroEntradaDTO entrada, Usuario usuario){
        return Carro.builder()
                .ano(entrada.ano())
                .modelo(entrada.modelo())
                .placa(entrada.placa())
                .kmRodados(entrada.kmRodados())
                .email(entrada.email())
                .kmRodados(entrada.kmRodados())
                .usuario(usuario)
                .build();
    }

    public Carro atualizar(CarroAtualizacaoDTO atl, Carro carro){
        return Carro.builder()
                .id(carro.getId())
                .ano(atl.ano())
                .modelo(atl.modelo())
                .placa(atl.placa())
                .kmRodados(atl.kmRodados())
                .email(atl.email())
                .build();
    }
}
