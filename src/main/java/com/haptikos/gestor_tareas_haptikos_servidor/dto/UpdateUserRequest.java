package com.haptikos.gestor_tareas_haptikos_servidor.dto;

public class UpdateUserRequest {
    private String name;
    private Boolean notifyTaskReminders;
    private Boolean notifyTaskCompleted;
    private Boolean notifyNewMembers;
    private String homeId;

    // Genera todos los Getters y Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Boolean getNotifyTaskReminders() { return notifyTaskReminders; }
    public void setNotifyTaskReminders(Boolean notifyTaskReminders) { this.notifyTaskReminders = notifyTaskReminders; }

    public Boolean getNotifyTaskCompleted() { return notifyTaskCompleted; }
    public void setNotifyTaskCompleted(Boolean notifyTaskCompleted) { this.notifyTaskCompleted = notifyTaskCompleted; }

    public Boolean getNotifyNewMembers() { return notifyNewMembers; }
    public void setNotifyNewMembers(Boolean notifyNewMembers) { this.notifyNewMembers = notifyNewMembers; }

    public String getHomeId() {
        return homeId;
    }

    public void setHomeId(String homeId) {
        this.homeId = homeId;
    }
}