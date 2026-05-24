package com.haptikos.gestor_tareas_haptikos_servidor.controller;

import com.haptikos.gestor_tareas_haptikos_servidor.model.User;
import com.haptikos.gestor_tareas_haptikos_servidor.repository.UserRepository;
import com.haptikos.gestor_tareas_haptikos_servidor.service.JwtService;
import com.haptikos.gestor_tareas_haptikos_servidor.service.PasswordResetService;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final PasswordResetService passwordResetService;

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    public AuthController(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            PasswordResetService passwordResetService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.passwordResetService = passwordResetService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body(Map.of("error", "El correo ya está registrado"));
        }
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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                String token = jwtService.generateToken(email);
                return ResponseEntity.ok(Map.of(
                        "token", token,
                        "name", user.getName(),
                        "id", user.getId(),
                        "email", user.getEmail()
                ));
            }
        }
        return ResponseEntity.status(401).body(Map.of("error", "Correo o contraseña incorrectos"));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> body) {
        String email = body.get("email");

        String genericResponse = "Si el correo está registrado, recibirás un código en breve";

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return ResponseEntity.ok(Map.of("message", genericResponse));
        }

        User user = userOptional.get();
        String resetCode = String.format("%06d", new Random().nextInt(999999));

        user.setResetToken(resetCode);
        user.setResetTokenExpiry(LocalDateTime.now().plusMinutes(15));
        userRepository.save(user);

        try {
            passwordResetService.sendResetEmail(email, resetCode);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "No se pudo enviar el correo"));
        }

        return ResponseEntity.ok(Map.of("message", genericResponse));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        String newPassword = body.get("newPassword");

        Optional<User> userOptional = userRepository.findByResetToken(token);
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Código inválido"));
        }

        User user = userOptional.get();
        if (user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body(Map.of("error", "El código ha expirado"));
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);
        userRepository.save(user);

        return ResponseEntity.ok(Map.of("message", "Contraseña actualizada con éxito"));
    }
}