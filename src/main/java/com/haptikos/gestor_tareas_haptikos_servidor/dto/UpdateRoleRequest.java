package com.haptikos.gestor_tareas_haptikos_servidor.dto;

public class UpdateRoleRequest {

    private String role;
    private String actorUserId;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getActorUserId() {
        return actorUserId;
    }

    public void setActorUserId(String actorUserId) {
        this.actorUserId = actorUserId;
    }
}
