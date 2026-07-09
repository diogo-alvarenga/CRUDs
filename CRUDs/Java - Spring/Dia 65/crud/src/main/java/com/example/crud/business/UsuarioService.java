package com.example.crud.business;

import com.example.crud.dto.UsuarioAtualizacaoDTO;
import com.example.crud.dto.UsuarioEntradaDTO;
import com.example.crud.dto.UsuarioSaidaDTO;
import com.example.crud.exception.EmailJaExistenteException;
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
        if(repository.findByEmail(entrada.email()).isPresent()) throw new EmailJaExistenteException();
        Usuario usuario = UsuarioMapper.toEntity(entrada);
        usuario.setSenha(encoder.encode(entrada.senha()));
        repository.saveAndFlush(usuario);
        log.info("Usuario {} adicionado com sucesso.", usuario.getNome());

        return UsuarioMapper.toDto(usuario);
    }

    public UsuarioSaidaDTO buscarUsuario(Long id){
        return UsuarioMapper.toDto(buscarUsuarioPorId(id));
    }

    public void deletarUsuario(Long id){
        Usuario usuario = buscarUsuarioPorId(id);
        repository.deleteById(id);
        log.info("Usuario {} deletado com sucesso.", usuario.getId());
    }

    public UsuarioSaidaDTO atualizarUsuario(UsuarioAtualizacaoDTO atl, Long id){
        Usuario usuario = buscarUsuarioPorId(id);
        Usuario usuarioAtl = UsuarioMapper.atualizar(atl, usuario);
        usuarioAtl.setSenha(atl.senha()!=null?encoder.encode(atl.senha()):usuario.getSenha());
        repository.saveAndFlush(usuarioAtl);
        log.info("Usuario {} atualizado com sucesso.", usuarioAtl.getNome());

        return UsuarioMapper.toDto(usuarioAtl);
    }

    public List<UsuarioSaidaDTO> listarTodos(){
        return repository.findAll()
                .stream()
                .map(UsuarioMapper::toDto)
                .toList();
    }

    private Usuario buscarUsuarioPorId(Long id){
        return repository.findById(id).orElseThrow(
                () -> new UsuarioNaoEncontradoException()
        );
    }

}
