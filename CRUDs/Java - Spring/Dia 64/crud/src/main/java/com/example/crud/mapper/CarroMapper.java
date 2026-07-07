package com.example.crud.mapper;

import lombok.experimental.UtilityClass;
import com.example.crud.dto.CarroEntradaDTO;
import com.example.crud.dto.CarroAtualizacaoDTO;
import com.example.crud.ifrastructure.entity.Carro;
import com.example.crud.infrastructure.entity.Usuario;

@UtilityClass
public class CarroMapper {
    public Carro toEntity(CarroEntradaDTO entrada, Usuario usuario){
        return Carro.builder()
                .placa(entrada.placa())
                .ano(entrada.ano())
                .modelo(entrada.modelo())
                .email(entrada.email())
                .marca(entrada.marca())
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
                .placa(atl.novaPlaca()!=null?atl.novaPlaca(): carro.getPlaca())
                .usuario(carro.getUsuario())
                .build();
    }
}
