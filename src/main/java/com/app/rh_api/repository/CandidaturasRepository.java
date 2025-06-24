package com.app.rh_api.repository;

import com.app.rh_api.model.Candidatura;
import com.app.rh_api.model.Usuario;
import com.app.rh_api.model.Vaga;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface  CandidaturasRepository extends JpaRepository<Candidatura, Long> {
    boolean existsByUsuarioAndVaga(Usuario usuario, Vaga vaga);
    List<Candidatura> findByUsuarioId(Long id);
}
