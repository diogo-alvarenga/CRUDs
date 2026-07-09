package com.example.crud.business;

import com.example.crud.dto.CarroAtualizacaoDTO;
import com.example.crud.dto.CarroEntradaDTO;
import com.example.crud.exception.CarroNaoEncontradoException;
import com.example.crud.exception.EmailNaoCadastradoException;
import com.example.crud.exception.UsuarioNaoEncontradoException;
import com.example.crud.infrastructure.entity.Carro;
import com.example.crud.infrastructure.entity.Usuario;
import com.example.crud.infrastructure.repository.CarroRepository;
import com.example.crud.infrastructure.repository.UsuarioRepository;
import com.example.crud.mapper.CarroMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarroService {

    private final CarroRepository carroRepository;
    private final UsuarioRepository usuarioRepository;
    private static final Logger log = LoggerFactory.getLogger(CarroService.class);

    public Carro adicionarCarro(CarroEntradaDTO entrada){
        Usuario usuario = usuarioRepository.findByEmail(entrada.email())
                .orElseThrow(EmailNaoCadastradoException::new);
        Carro carro = CarroMapper.toEntity(entrada, usuario);
        carroRepository.saveAndFlush(carro);
        log.info("Carro {} de {} adicionado com sucesso.",carro.getModelo(),usuario.getNome());

        return carro;
    }

    public Carro atualizarCarro(CarroAtualizacaoDTO atl, Long id){
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(UsuarioNaoEncontradoException::new);
        Carro carro = carroRepository.findByPlaca(atl.placa()).orElseThrow(CarroNaoEncontradoException::new);
        Carro carroAtl = CarroMapper.atualizar(atl, carro);
        carroAtl.setUsuario(usuario);

        return carroRepository.saveAndFlush(carroAtl);
    }

    public void deletarUsuario(Long id){
        Carro carro = buscarCarroPorId(id);
        carroRepository.deleteById(id);
        log.info("Carro {} deletado com sucesso.",carro.getModelo());
    }

    public List<Carro> listarTodos(){
        return carroRepository.findAll();
    }

    public Carro buscarCarroPorId(Long id){
        return carroRepository.findById(id).orElseThrow(CarroNaoEncontradoException::new);
    }
}
