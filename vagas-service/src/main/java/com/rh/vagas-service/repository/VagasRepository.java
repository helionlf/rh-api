package com.rh.vagas_service.repository;

import com.rh.vagas_service.model.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VagasRepository extends JpaRepository<Vaga, Long> {
    
}
