package com.haptikos.gestor_tareas_haptikos_servidor.repository;

import com.haptikos.gestor_tareas_haptikos_servidor.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {
}