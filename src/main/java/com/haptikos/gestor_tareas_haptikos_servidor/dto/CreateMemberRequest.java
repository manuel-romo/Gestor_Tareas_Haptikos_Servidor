package com.haptikos.gestor_tareas_haptikos_servidor.dto;

public class CreateMemberRequest {
    private String id;
    private String userId;
    private String homeId;
    private String name;
    private String lastName;
    private String colorHex;
    private String role;
    private String status;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getHomeId() { return homeId; }
    public void setHomeId(String homeId) { this.homeId = homeId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getColorHex() { return colorHex; }
    public void setColorHex(String colorHex) { this.colorHex = colorHex; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}