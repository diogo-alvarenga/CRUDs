package com.example.crud.mapper;

import com.example.crud.dto.CarroAtualizacaoDTO;
import com.example.crud.dto.CarroEntradaDTO;
import com.example.crud.infrastructure.entity.Carro;
import com.example.crud.infrastructure.entity.Usuario;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CarroMapper {

    public Carro toEntity(CarroEntradaDTO dto, Usuario usuario){
        return Carro.builder()
                .ano(dto.ano())
                .modelo(dto.modelo())
                .marca(dto.marca())
                .email(dto.email())
                .placa(dto.placa())
                .usuario(usuario)
                .build();
    }

    public Carro atualizar(CarroAtualizacaoDTO atl, Carro carro){
        return Carro.builder()
                .id(carro.getId())
                .modelo(atl.modelo()!=null?atl.modelo():carro.getModelo())
                .marca(atl.marca()!=null?atl.marca():carro.getMarca())
                .email(atl.email()!=null?atl.email():carro.getEmail())
                .ano(atl.ano()!=null?atl.ano():carro.getAno())
                .placa(atl.novaPlaca()!=null?atl.novaPlaca(): atl.placa())
                .usuario(carro.getUsuario())
                .build();
    }
}
