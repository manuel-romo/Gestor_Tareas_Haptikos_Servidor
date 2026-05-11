package com.haptikos.gestor_tareas_haptikos_servidor.dto;

public class UpdateRoomRequest {
    private String name;
    private String icon;
    private String colorHex;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }

    public String getColorHex() { return colorHex; }
    public void setColorHex(String colorHex) { this.colorHex = colorHex; }
}