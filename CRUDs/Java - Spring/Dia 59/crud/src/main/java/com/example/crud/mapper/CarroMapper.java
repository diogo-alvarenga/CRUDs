package com.example.crud.mapper;

import com.example.crud.dto.AtualizacaoCarroDTO;
import com.example.crud.dto.EntradaCarroDTO;
import com.example.crud.dto.SaidaCarroDTO;
import com.example.crud.dto.SaidaUsuarioDTO;
import com.example.crud.infrastructure.entity.Carro;
import com.example.crud.infrastructure.entity.Usuario;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CarroMapper {

    public SaidaCarroDTO entityParaDto(Carro carro){
        return SaidaCarroDTO.builder()
                .id(carro.getId())
                .usuario(carro.getUsuario())
                .modelo(carro.getModelo())
                .ano(carro.getAno())
                .kmRodados(carro.getKmRodados())
                .fabricante(carro.getFabricante())
                .build();
    }

    public Carro dtoParaEntity(EntradaCarroDTO entrada, Usuario usuario){
        return Carro.builder()
                .modelo(entrada.modelo())
                .ano(entrada.ano())
                .kmRodados(entrada.kmRodados())
                .fabricante(entrada.fabricante())
                .usuario(usuario)
                .build();
    }

    public Carro atualizarCarro(AtualizacaoCarroDTO atl, Carro carro){
        return Carro.builder()
                .id(carro.getId())
                .kmRodados(atl.kmRodados() != null? atl.kmRodados() : carro.getKmRodados())
                .ano(atl.ano() != null? atl.ano() : carro.getAno())
                .modelo(atl.modelo() !=null? atl.modelo() : carro.getModelo())
                .fabricante(atl.fabricante() !=null? atl.fabricante() : carro.getFabricante())
                //.usuario(atl.usuario() != null? atl.usuario() : carro.getUsuario())
                .build();
    }
}
