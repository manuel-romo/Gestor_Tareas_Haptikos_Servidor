package com.haptikos.gestor_tareas_haptikos_servidor.dto;

public class UpdateHomeRequest {
    private String name;
    private String description;
    private Boolean isPrivate;
    private Boolean notifyTaskReminders;
    private Boolean notifyTaskCompleted;
    private Boolean notifyNewMembers;
    private Boolean notifyAllMembers;
    private Boolean forceSettings;
    private String editPermission;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public Boolean getNotifyNewMembers() {
        return notifyNewMembers;
    }

    public void setNotifyNewMembers(Boolean notifyNewMembers) {
        this.notifyNewMembers = notifyNewMembers;
    }

    public Boolean getNotifyTaskReminders() {
        return notifyTaskReminders;
    }

    public void setNotifyTaskReminders(Boolean notifyTaskReminders) {
        this.notifyTaskReminders = notifyTaskReminders;
    }

    public Boolean getNotifyTaskCompleted() {
        return notifyTaskCompleted;
    }

    public void setNotifyTaskCompleted(Boolean notifyTaskCompleted) {
        this.notifyTaskCompleted = notifyTaskCompleted;
    }

    public Boolean getNotifyAllMembers() {
        return notifyAllMembers;
    }

    public void setNotifyAllMembers(Boolean notifyAllMembers) {
        this.notifyAllMembers = notifyAllMembers;
    }

    public Boolean getForceSettings() {
        return forceSettings;
    }

    public void setForceSettings(Boolean forceSettings) {
        this.forceSettings = forceSettings;
    }

    public String getEditPermission() { return editPermission; }

    public void setEditPermission(String editPermission) { this.editPermission = editPermission; }

}