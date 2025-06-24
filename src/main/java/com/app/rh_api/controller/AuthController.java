package com.app.rh_api.controller;

import com.app.rh_api.dto.LoginRequest;
import com.app.rh_api.dto.LoginResponse;
import com.app.rh_api.model.Usuario;
import com.app.rh_api.repository.UsuariosRepository;
import com.app.rh_api.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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

        String token = jwtUtil.generateToken(usuario.getEmail());
        return new LoginResponse(token);
    }
}
