package com.haptikos.gestor_tareas_haptikos_servidor.repository;

import com.haptikos.gestor_tareas_haptikos_servidor.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}