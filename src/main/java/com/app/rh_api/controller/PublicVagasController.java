package com.app.rh_api.controller;

import com.app.rh_api.service.VagasService;
import com.app.rh_api.model.Vaga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/public/vagas")
public class PublicVagasController {

    @Autowired
    private VagasService service;
    
    @GetMapping
    public Page<Vaga> getAll(Pageable pageable) {
        return service.listAll(pageable);
    }

    @GetMapping("/{id}")
    public Vaga getOne(@PathVariable Long id) {
        return service.get(id);
    }
}
