package com.app.rh_api.repository;

import com.app.rh_api.model.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VagasRepository extends JpaRepository<Vaga, Long> {
    
}
