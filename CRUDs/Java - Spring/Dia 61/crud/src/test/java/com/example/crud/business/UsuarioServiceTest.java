package com.example.crud.business;

import com.example.crud.dto.UsuarioAtualizacaoDTO;
import com.example.crud.dto.UsuarioEntradaDTO;
import com.example.crud.dto.UsuarioSaidaDTO;
import com.example.crud.exception.EmailJaCadastradoException;
import com.example.crud.exception.UsuarioNaoEncontradoException;
import com.example.crud.infrastructure.entity.Usuario;
import com.example.crud.infrastructure.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService service;

    @Mock
    private UsuarioRepository repository;

    @Mock
    private PasswordEncoder encoder;

    private Usuario usuario;
    private UsuarioEntradaDTO entradaDTO;
    private UsuarioAtualizacaoDTO atualizacaoDTO;

    @BeforeEach
    void setup() {

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("João");
        usuario.setEmail("joao@email.com");
        usuario.setSenha("123456");

        entradaDTO = UsuarioEntradaDTO.builder()
                .nome("João")
                .email("joao@email.com")
                .password("123456")
                .build();

        atualizacaoDTO = UsuarioAtualizacaoDTO.builder()
                .nome("João Atualizado")
                .email("joao@email.com")
                .password("654321")
                .build();
    }

    @Test
    void deveAdicionarUsuarioComSucesso() {

        when(repository.findByEmail(entradaDTO.email()))
                .thenReturn(Optional.empty());

        when(encoder.encode(any()))
                .thenReturn("senhaCriptografada");

        when(repository.saveAndFlush(any()))
                .thenReturn(usuario);

        UsuarioSaidaDTO dto = service.adicionarUsuario(entradaDTO);

        assertNotNull(dto);

        verify(repository).findByEmail(entradaDTO.email());
        verify(repository).saveAndFlush(any());
        verify(encoder).encode("123456");
    }

    @Test
    void deveLancarExcecaoQuandoEmailJaExiste() {

        when(repository.findByEmail(entradaDTO.email()))
                .thenReturn(Optional.of(usuario));

        assertThrows(
                EmailJaCadastradoException.class,
                () -> service.adicionarUsuario(entradaDTO)
        );

        verify(repository, never()).saveAndFlush(any());
    }

    @Test
    void deveAtualizarUsuarioComSucesso() {

        when(repository.findById(1L))
                .thenReturn(Optional.of(usuario));

        when(encoder.encode(any()))
                .thenReturn("novaSenha");

        when(repository.saveAndFlush(any()))
                .thenReturn(usuario);

        UsuarioSaidaDTO dto = service.atualizarUsuario(atualizacaoDTO, 1L);

        assertNotNull(dto);

        verify(repository).findById(1L);
        verify(repository).saveAndFlush(any());
        verify(encoder).encode("654321");
    }

    @Test
    void deveDeletarUsuarioComSucesso() {

        when(repository.findById(1L))
                .thenReturn(Optional.of(usuario));

        doNothing().when(repository).deleteById(1L);

        service.deletarUsuarioPorId(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    void deveBuscarUsuarioPorId() {

        when(repository.findById(1L))
                .thenReturn(Optional.of(usuario));

        UsuarioSaidaDTO dto = service.buscarUsuario(1L);

        assertNotNull(dto);
        assertEquals(usuario.getNome(), dto.getNome());

        verify(repository).findById(1L);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontradoAoBuscar() {

        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                UsuarioNaoEncontradoException.class,
                () -> service.buscarUsuario(1L)
        );
    }

    @Test
    void deveListarTodosUsuarios() {

        Usuario usuario2 = new Usuario();
        usuario2.setId(2L);
        usuario2.setNome("Maria");
        usuario2.setEmail("maria@email.com");

        when(repository.findAll())
                .thenReturn(List.of(usuario, usuario2));

        List<UsuarioSaidaDTO> lista = service.listarTodos();

        assertEquals(2, lista.size());

        verify(repository).findAll();
    }

    @Test
    void deveBuscarUsuarioPorIdInternamente() {

        when(repository.findById(1L))
                .thenReturn(Optional.of(usuario));

        Usuario retorno = service.buscarUsuarioPorId(1L);

        assertEquals(usuario, retorno);
    }

    @Test
    void deveLancarExcecaoAoBuscarUsuarioPorIdInternamente() {

        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                UsuarioNaoEncontradoException.class,
                () -> service.buscarUsuarioPorId(1L)
        );
    }
}