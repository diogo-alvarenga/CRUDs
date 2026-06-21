package com.example.crud.business;

import com.example.crud.dto.AtualizacaoCarroDTO;
import com.example.crud.dto.EntradaCarroDTO;
import com.example.crud.dto.SaidaCarroDTO;
import com.example.crud.dto.SaidaUsuarioDTO;
import com.example.crud.exception.CarroInexistenteException;
import com.example.crud.exception.UsuarioNaoEncontradoException;
import com.example.crud.infrastructure.entity.Carro;
import com.example.crud.infrastructure.entity.Usuario;
import com.example.crud.mapper.CarroMapper;
import com.example.crud.repository.CarroRepository;
import com.example.crud.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CarroService {

    private final CarroRepository repository;
    private final UsuarioService usuarioService;
    private static final Logger log = LoggerFactory.getLogger(CarroService.class);

    public SaidaCarroDTO salvarCarro(EntradaCarroDTO entrada){
        Usuario usuario = usuarioService.buscarUsuarioPorId(entrada.idUsuario());
        log.info("Usuario {} encontrado. Pronto para adicionar o veiculo...",usuario.getNome());
        log.info("Veiculo {} adicionado.",entrada.modelo());
        return CarroMapper.entityParaDto(
                repository.saveAndFlush(
                        CarroMapper.dtoParaEntity(entrada,usuario)));
    }

    public void deletarCarro(Long id){
        if(repository.existsById(id)){
            log.info("Carro de id {} deletado com sucesso.",id);
            repository.deleteById(id);
        }else{
            throw new CarroInexistenteException();
        }
    }

    public SaidaCarroDTO buscarCarro(Long id){
        return CarroMapper.entityParaDto(buscarCarroPorId(id));
    }

    public SaidaCarroDTO atualizarCarro(Long id, AtualizacaoCarroDTO atl){
        Carro carroBanco = buscarCarroPorId(id);
        Carro carroAtualizado = CarroMapper.atualizarCarro(atl, carroBanco);
        log.info("Carro {} atualizado com sucesso.",carroAtualizado.getModelo());
        return CarroMapper.entityParaDto(repository.saveAndFlush(carroAtualizado));
    }

    public List<SaidaCarroDTO> listarTodos(){
        return repository.findAll()
                .stream()
                .map(CarroMapper::entityParaDto)
                .toList();
    }

    private Carro buscarCarroPorId(Long id){
        log.info("Buscando carro pelo id {}",id);
        return repository.findById(id).orElseThrow(
                () -> new CarroInexistenteException()
        );
    }

}
