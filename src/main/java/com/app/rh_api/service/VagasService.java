package com.app.rh_api.service;

import com.app.rh_api.model.Vaga;
import com.app.rh_api.repository.VagasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class VagasService {

    @Autowired
    private VagasRepository repository;

    // CRUD Vagas

    public Page<Vaga> listAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Vaga get(Long id) {
        return repository.findById(id).orElse(null);
    }
    
    public Vaga save(Vaga vaga) {
        return repository.save(vaga);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    // Serviço de candidaturas

    public String candidatar(Long id) {
        return "Você se candidatou á esta vaga!";
    }

}
