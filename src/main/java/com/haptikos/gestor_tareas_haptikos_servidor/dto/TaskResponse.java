package com.haptikos.gestor_tareas_haptikos_servidor.dto;

public class TaskResponse {
    private String id;
    private String title;

    public TaskResponse(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
}