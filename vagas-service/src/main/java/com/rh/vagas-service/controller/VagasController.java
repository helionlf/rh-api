package com.rh.vagas_service.controller;

import com.rh.vagas_service.service.VagasService;
import com.rh.vagas_service.model.Vaga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/rh-admin/vagas")
public class VagasController {

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

    @PostMapping
    public Vaga create(@RequestBody Vaga vaga) {
        return service.save(vaga);
    }

    @PutMapping("/{id}")
    public Vaga update(@PathVariable Long id, @RequestBody Vaga vaga) {
        vaga.setId(id);
        return service.save(vaga);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
