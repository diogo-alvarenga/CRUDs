package com.example.crud.business;

import com.example.crud.dto.UsuarioAtualizacaoDTO;
import com.example.crud.dto.UsuarioEntradaDTO;
import com.example.crud.dto.UsuarioSaidaDTO;
import com.example.crud.exception.EmailJaCadastradoException;
import com.example.crud.exception.UsuarioNaoEncontradoException;
import com.example.crud.infrastructure.entity.Usuario;
import com.example.crud.infrastructure.repository.UsuarioRepository;
import com.example.crud.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;
    private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);

    public UsuarioSaidaDTO adicionarUsuario(UsuarioEntradaDTO entrada){
        if(repository.findByEmail(entrada.email()).isPresent()) throw new EmailJaCadastradoException();
        Usuario usuario = UsuarioMapper.toEntity(entrada);
        usuario.setSenha(encoder.encode(entrada.password()));
        repository.saveAndFlush(usuario);
        log.info("Usuario {} adicionado com sucesso", entrada.nome());

        return UsuarioMapper.toDTO(usuario);
    }

    public UsuarioSaidaDTO atualizarUsuario(UsuarioAtualizacaoDTO atl, Long id){
        Usuario usuario = buscarUsuarioPorId(id);
        Usuario usuarioAtl = UsuarioMapper.atualizar(atl, usuario);
        usuarioAtl.setSenha(atl.password()!=null? encoder.encode(atl.password()) : usuario.getSenha());
        repository.saveAndFlush(usuarioAtl);
        log.info("Usuario {} atualizado com sucesso", usuarioAtl.getNome());

        return UsuarioMapper.toDTO(usuarioAtl);
    }

    public void deletarUsuarioPorId(Long id){
        Usuario usuario = buscarUsuarioPorId(id);
        repository.deleteById(id);
        log.info("Usuario {} deletado com sucesso", usuario.getNome());
    }

    public UsuarioSaidaDTO buscarUsuario(Long id){
        return UsuarioMapper.toDTO(buscarUsuarioPorId(id));
    }

    public List<UsuarioSaidaDTO> listarTodos(){
        return repository.findAll()
                .stream()
                .map(UsuarioMapper::toDTO)
                .toList();
    }
    public Usuario buscarUsuarioPorId(Long id){
        return repository.findById(id).orElseThrow(
                ()-> new UsuarioNaoEncontradoException()
        );
    }

}
