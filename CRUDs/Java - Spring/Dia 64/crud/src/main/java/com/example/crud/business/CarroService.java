package com.example.crud.business;

import com.example.crud.dto.CarroAtualizacaoDTO;
import com.example.crud.dto.CarroEntradaDTO;
import com.example.crud.exception.CarroNaoEncontradoException;
import com.example.crud.exception.UsuarioNaoEncontradoException;
import com.example.crud.infrastructure.entity.Usuario;
import com.example.crud.ifrastructure.entity.Carro;
import com.example.crud.ifrastructure.repository.CarroRepository;
import com.example.crud.ifrastructure.repository.UsuarioRepository;
import com.example.crud.mapper.CarroMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarroService {

    private final CarroRepository carroRepository;
    private final UsuarioRepository usuarioRepository;

    public Carro adicionarCarro(CarroEntradaDTO carro){
        Usuario usuario = buscarUsuarioPorEmail(carro.email());
        return carroRepository.saveAndFlush(CarroMapper.toEntity(carro,usuario));
    }

    public Carro atualizarCarro(Long id, CarroAtualizacaoDTO entrada){
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(UsuarioNaoEncontradoException::new);
        Carro carro = carroRepository.findByPlaca(entrada.placa()).orElseThrow(
                () -> new CarroNaoEncontradoException()
        );
        return carroRepository.saveAndFlush(CarroMapper.atualizar(entrada, carro));
    }

    public void deletarCarro(Long id){
        carroRepository.deleteById(id);
    }

    public Carro buscarCarro(Long id){
        return carroRepository.findById(id).orElseThrow(
                () -> new CarroNaoEncontradoException()
        );
    }

    public List<Carro> listarCarros(){
        return carroRepository.findAll();
    }

    public Usuario buscarUsuarioPorEmail(String email){
        return usuarioRepository.findByEmail(email).orElseThrow(
                () -> new UsuarioNaoEncontradoException()
        );
    }
}
