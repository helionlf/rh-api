package com.app.rh_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.rh_api.dto.LoginRequest;
import com.app.rh_api.dto.LoginResponse;
import com.app.rh_api.model.Usuario;
import com.app.rh_api.repository.UsuariosRepository;
import com.app.rh_api.security.JwtUtil;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping
public class AuthController {
    
    @Autowired
    private UsuariosRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        Usuario usuario = repository.findByEmail(loginRequest.getEmail())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        if (!passwordEncoder.matches(loginRequest.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Senha inválida");
        }

        // 👇 Define a role com base no email (ou como preferir)
        String role = loginRequest.getEmail().equals("helio@gmail.com") ? "admin" : "user";

        // 👇 Gera o token com a role embutida
        String token = jwtUtil.generateToken(usuario.getEmail(), role);

        return new LoginResponse(token);
    }
}
