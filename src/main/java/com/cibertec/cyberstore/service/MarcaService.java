package com.cibertec.cyberstore.service;

import com.cibertec.cyberstore.model.Marca;
import com.cibertec.cyberstore.repository.MarcaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MarcaService {

    private final MarcaRepository marcaRepository;

    public MarcaService(MarcaRepository marcaRepository) {
        this.marcaRepository = marcaRepository;
    }

    @Transactional(readOnly = true)
    public List<Marca> listarTodas() {
        return marcaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Marca buscarPorId(Long id) {
        return marcaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Marca no encontrada: " + id));
    }
}
