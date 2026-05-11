package com.haptikos.gestor_tareas_haptikos_servidor.controller;

import com.haptikos.gestor_tareas_haptikos_servidor.dto.CreateRoomRequest;
import com.haptikos.gestor_tareas_haptikos_servidor.dto.UpdateRoomRequest;
import com.haptikos.gestor_tareas_haptikos_servidor.model.Room;
import com.haptikos.gestor_tareas_haptikos_servidor.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    public ResponseEntity<?> createRoom(@RequestBody CreateRoomRequest request) {
        try {
            Room createdRoom = roomService.createRoom(request);

            return ResponseEntity.ok(Map.of(
                    "message", "Habitación creada con éxito",
                    "roomId", createdRoom.getId()
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al crear la habitación: " + e.getMessage()));
        }
    }

    @PatchMapping("/{roomId}")
    public ResponseEntity<?> updateRoom(
            @PathVariable String roomId,
            @RequestBody UpdateRoomRequest request) {
        try {
            Room updatedRoom = roomService.updateRoom(roomId, request);

            return ResponseEntity.ok(Map.of(
                    "message", "Habitación actualizada con éxito",
                    "roomId", updatedRoom.getId()
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al actualizar la habitación: " + e.getMessage()));
        }
    }
}