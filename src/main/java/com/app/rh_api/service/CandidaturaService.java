package com.app.rh_api.service;

import com.app.rh_api.model.Candidatura;
import com.app.rh_api.repository.CandidaturasRepository;
import com.app.rh_api.repository.UsuariosRepository;
import com.app.rh_api.repository.VagasRepository;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app.rh_api.model.Usuario;
import com.app.rh_api.model.Vaga;


@Service
public class CandidaturaService {
    
    @Autowired
    private CandidaturasRepository candidaturasRepository;

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private VagasRepository vagasRepository;

    public List<Candidatura> listarCandidaturas(String email) {
        Usuario usuario = usuariosRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        return candidaturasRepository.findByUsuarioId(usuario.getId());
    }

    public Candidatura realizarCandidatura(String email, Long id_vaga) {
        Usuario usuario = usuariosRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Vaga vaga = vagasRepository.findById(id_vaga)
            .orElseThrow(() -> new RuntimeException("Vaga não encontrada"));

        boolean jaCandidatado = candidaturasRepository.existsByUsuarioAndVaga(usuario, vaga);
        if (jaCandidatado) {
            throw new RuntimeException("Usuário já se candidatou para essa vaga.");
        }

        Candidatura candidatura = new Candidatura();
        candidatura.setVaga(vaga);
        candidatura.setUsuario(usuario);

        return candidaturasRepository.save(candidatura);

    }
}
