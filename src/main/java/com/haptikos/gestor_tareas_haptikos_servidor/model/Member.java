package com.haptikos.gestor_tareas_haptikos_servidor.model;

import jakarta.persistence.*;

@Entity
@Table(name = "members")
public class Member {
    @Id
    private String id;
    private String userId;
    private String name;
    private String lastName;
    private String colorHex;
    private String role;
    private String status;

    @ManyToOne
    @JoinColumn(name = "home_id")
    private Home home;

    private boolean notifyTaskReminders = true;
    private boolean notifyTaskCompleted = true;
    private boolean notifyNewMembers = true;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }

    public Home getHome() {
        return home;
    }

    public void setHome(Home home) {
        this.home = home;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getColorHex() {
        return colorHex;
    }

    public void setColorHex(String colorHex) {
        this.colorHex = colorHex;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public boolean isNotifyTaskReminders() { return notifyTaskReminders; }

    public void setNotifyTaskReminders(boolean notifyTaskReminders) { this.notifyTaskReminders = notifyTaskReminders; }

    public boolean isNotifyTaskCompleted() { return notifyTaskCompleted; }

    public void setNotifyTaskCompleted(boolean notifyTaskCompleted) { this.notifyTaskCompleted = notifyTaskCompleted; }

    public boolean isNotifyNewMembers() { return notifyNewMembers; }

    public void setNotifyNewMembers(boolean notifyNewMembers) { this.notifyNewMembers = notifyNewMembers; }
}