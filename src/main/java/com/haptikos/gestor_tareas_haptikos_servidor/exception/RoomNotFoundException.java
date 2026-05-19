package com.haptikos.gestor_tareas_haptikos_servidor.exception;

public class RoomNotFoundException extends RuntimeException {
    public RoomNotFoundException(String roomId) {
        super("Habitación no encontrada: " + roomId);
    }
}