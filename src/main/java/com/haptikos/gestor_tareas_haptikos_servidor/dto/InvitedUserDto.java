package com.haptikos.gestor_tareas_haptikos_servidor.dto;

public class InvitedUserDto {
    private String id;
    private String title;
    private String subtitle;
    private String userId;

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }

}