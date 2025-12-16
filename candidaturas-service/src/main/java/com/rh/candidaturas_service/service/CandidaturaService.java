package com.rh.candidaturas_service.service;

import com.rh.candidaturas_service.model.Candidatura;
import com.rh.candidaturas_service.repository.CandidaturasRepository;
import com.rh.candidaturas_service.dto.UsuarioDTO;
import com.rh.candidaturas_service.dto.VagaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class CandidaturaService {

    @Autowired
    private RestTemplate restTemplate;

    private final String usuariosServiceUrl = "http://usuarios-service:8081";
    private final String vagasServiceUrl = "http://vagas-service:8082";

    @Autowired
    private CandidaturasRepository candidaturasRepository;

    @Autowired
    private EmailService emailService;

    public List<Candidatura> listarCandidaturas(String email) {
        String url = usuariosServiceUrl + "/email/" + email;
        ResponseEntity<UsuarioDTO> response = restTemplate.getForEntity(url, UsuarioDTO.class);
        UsuarioDTO usuario = response.getBody();

        if (usuario == null) {
            throw new RuntimeException("Usuário não encontrado");
        }

        return candidaturasRepository.findByUsuarioId(usuario.getId());
    }

    public Candidatura realizarCandidatura(String email, Long id_vaga, MultipartFile curriculo) {
        String usuarioUrl = usuariosServiceUrl + "/email/" + email;
        UsuarioDTO usuario = restTemplate.getForObject(usuarioUrl, UsuarioDTO.class);

        if (usuario == null) {
            throw new RuntimeException("Usuário não encontrado");
        }

        String vagaUrl = vagasServiceUrl + "/" + id_vaga;
        VagaDTO vaga = restTemplate.getForObject(vagaUrl, VagaDTO.class);

        if (vaga == null) {
            throw new RuntimeException("Vaga não encontrada");
        }

        boolean jaCandidatado = candidaturasRepository.existsByUsuarioIdAndVagaId(usuario.getId(), vaga.getId());
        if (jaCandidatado) {
            throw new RuntimeException("Usuário já se candidatou para essa vaga.");
        }

        Candidatura candidatura = new Candidatura();
        candidatura.setUsuarioId(usuario.getId());
        candidatura.setVagaId(vaga.getId());
        candidaturasRepository.save(candidatura);

        emailService.enviarEmail(vaga, usuario, curriculo);

        return candidatura;
    }
}
