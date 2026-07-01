package com.example.crud.business;

import com.example.crud.dto.AtualizacaoDTO;
import com.example.crud.dto.EntradaDTO;
import com.example.crud.dto.SaidaDTO;
import com.example.crud.exceptions.EmailJaExistenteException;
import com.example.crud.exceptions.UsuarioNaoEncontradoException;
import com.example.crud.infrastructure.entitys.Usuario;
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

    public SaidaDTO adicionarUsuario(EntradaDTO entrada){
        if(repository.findByEmail(entrada.email()).isPresent()) throw new EmailJaExistenteException();
        Usuario usuario = UsuarioMapper.toEntity(entrada);
        usuario.setSenha(encoder.encode(entrada.senha()));
        repository.saveAndFlush(usuario);
        log.info("Usuario {} adicionado com sucesso!",usuario.getNome());

        return UsuarioMapper.toDTO(usuario);
    }

    public void excluirUsuario(Long id){
        Usuario usuario = buscarUsuarioPorId(id);
        repository.deleteById(id);
        log.info("Usuario {} deletado com sucesso!",usuario.getNome());
    }

    public SaidaDTO atualizarUsuario(AtualizacaoDTO atl, Long id){
        Usuario usuario = buscarUsuarioPorId(id);
        Usuario usuarioAtl = UsuarioMapper.atualizar(atl, usuario);
        usuarioAtl.setSenha(atl.senha()!=null? encoder.encode(atl.senha()) : usuario.getSenha());
        repository.saveAndFlush(usuarioAtl);
        log.info("Usuario {} atualizado com sucesso!",usuarioAtl.getNome());

        return UsuarioMapper.toDTO(usuarioAtl);
    }

    public SaidaDTO buscarUsuario(Long id){
        return UsuarioMapper.toDTO(buscarUsuarioPorId(id));
    }

    public List<SaidaDTO> listarTodos(){
        return repository.findAll()
                .stream()
                .map(UsuarioMapper :: toDTO )
                .toList();
    }

    public Usuario buscarUsuarioPorId(Long id){
        return repository.findById(id).orElseThrow(
                ()-> new UsuarioNaoEncontradoException()
        );
    }
}
