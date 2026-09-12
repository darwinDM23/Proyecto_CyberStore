package com.cibertec.cyberstore.service;

import com.cibertec.cyberstore.model.Usuario;
import com.cibertec.cyberstore.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    @Transactional
    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
}
