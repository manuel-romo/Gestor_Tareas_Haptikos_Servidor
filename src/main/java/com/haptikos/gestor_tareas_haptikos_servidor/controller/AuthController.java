package com.haptikos.gestor_tareas_haptikos_servidor.controller;

import com.haptikos.gestor_tareas_haptikos_servidor.model.User;
import com.haptikos.gestor_tareas_haptikos_servidor.repository.UserRepository;
import com.haptikos.gestor_tareas_haptikos_servidor.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    // Inyección de dependencias
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    // Endpoint 1 de Registro
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body(Map.of("error", "El correo ya está registrado"));
        }

        user.setId(java.util.UUID.randomUUID().toString());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        String token = jwtService.generateToken(user.getEmail());

        userRepository.save(user);

        return ResponseEntity.ok(Map.of(
                "message", "Usuario registrado con éxito",
                "token", token,
                "id", user.getId(),
                "name", user.getName(),
                "email", user.getEmail()
        ));
    }

    // Endpoint 2 de Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        // Búsqueda de usuario en MySQL
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Comparación de contraseña encriptada
            if (passwordEncoder.matches(password, user.getPassword())) {

                // Login exitoso
                String token = jwtService.generateToken(email);

                // Devolución
                return ResponseEntity.ok(Map.of(
                        "token", token,
                        "name", user.getName(),
                        "id", user.getId(),
                        "email", user.getEmail()
                ));
            }
        }

        // Si el correo no existe o la contraseña es incorrecta
        return ResponseEntity.status(401).body(Map.of("error", "Correo o contraseña incorrectos"));
    }
}