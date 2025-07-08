package com.app.rh_api.controller;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.app.rh_api.security.JwtUtil;
import com.app.rh_api.service.CandidaturaService;
import com.app.rh_api.model.Candidatura;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("public/vagas")
public class CandidaturasController {
    
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CandidaturaService candidaturaService;

    @GetMapping("/aplicadas")
    public List<Candidatura> mostrarVagas(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Token não encontrado");
        }

        String token = authHeader.substring(7);
        String email = jwtUtil.extractEmail(token);

        return candidaturaService.listarCandidaturas(email);
    }

    @PostMapping("/{id}/candidatar")
    public Candidatura candidatar(@PathVariable Long id, @RequestParam MultipartFile curriculo, HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Token não encontrado");
        }

        String token = authHeader.substring(7);
        String email = jwtUtil.extractEmail(token);

        return candidaturaService.realizarCandidatura(email, id, curriculo);
    }

}
