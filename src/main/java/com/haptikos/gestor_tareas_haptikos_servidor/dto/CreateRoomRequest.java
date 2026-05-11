package com.haptikos.gestor_tareas_haptikos_servidor.dto;

public class CreateRoomRequest {
    private String id;
    private String name;
    private String icon;
    private String colorHex;
    private String homeId;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }

    public String getColorHex() { return colorHex; }
    public void setColorHex(String colorHex) { this.colorHex = colorHex; }

    public String getHomeId() { return homeId; }
    public void setHomeId(String homeId) { this.homeId = homeId; }
}