package com.haptikos.gestor_tareas_haptikos_servidor.dto;

import java.util.List;

public class CreateTaskRequest {
    public String id;
    public String title;
    public String description;
    public int points;
    public String priority;
    public String suggestedDay;
    public String recurrence;
    public String workMode;
    public int lastMemberIndex;
    public String roomId; // Solo el ID de la habitación
    public List<String> memberIds; // Solo los IDs de los miembros
}