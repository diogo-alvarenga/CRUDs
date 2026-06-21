package com.example.crud.business;

import com.example.crud.dto.AtualizacaoUsuarioDTO;
import com.example.crud.dto.EntradaUsuarioDTO;
import com.example.crud.dto.SaidaUsuarioDTO;
import com.example.crud.exception.EmailCadastradoException;
import com.example.crud.exception.UsuarioNaoEncontradoException;
import com.example.crud.infrastructure.entity.Usuario;
import com.example.crud.mapper.UsuarioMapper;
import com.example.crud.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UsuarioService {

    private final PasswordEncoder encoder;
    private final UsuarioRepository repository;
    private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);

    public SaidaUsuarioDTO adicionarUsuario(EntradaUsuarioDTO entrada){
        if(repository.findByEmail(entrada.email()).isPresent()) throw new EmailCadastradoException();
        Usuario usuario = UsuarioMapper.dtoParaEntity(entrada);
        usuario.setSenha(encoder.encode(entrada.senha()));
        repository.saveAndFlush(usuario);
        log.info("Usuario {} adicionado com sucesso.",usuario.getNome());
        return UsuarioMapper.entityParaDto(usuario);
    }

    public SaidaUsuarioDTO buscarUsuario(Long id){
        return UsuarioMapper.entityParaDto(buscarUsuarioPorId(id));
    }

    public SaidaUsuarioDTO atualizarUsuario(Long id, AtualizacaoUsuarioDTO atl){
        Usuario usuarioBanco = buscarUsuarioPorId(id);
        Usuario usuarioAtl = UsuarioMapper.atualizarUsuario(atl, usuarioBanco);
        usuarioAtl.setSenha(atl.senha()!=null?encoder.encode(atl.senha()) : usuarioBanco.getSenha());
        repository.saveAndFlush(usuarioAtl);
        log.info("Usuario {} atualizado com sucesso.",usuarioAtl.getNome());
        return(UsuarioMapper.entityParaDto(usuarioAtl));
    }

    public void deletarUsuario(Long id){
        if(repository.existsById(id)){
            log.info("Usuario de id {} deletado com sucesso.",id);
            repository.deleteById(id);
        }else{
            throw new UsuarioNaoEncontradoException();
        }
    }

    public List<SaidaUsuarioDTO> listarTodos(){

        return repository.findAll()
                .stream()
                .map( UsuarioMapper::entityParaDto)
                .toList();
    }

    public Usuario buscarUsuarioPorId(Long id){
        return repository.findById(id).orElseThrow(
                () -> new UsuarioNaoEncontradoException()
        );
    }

}
