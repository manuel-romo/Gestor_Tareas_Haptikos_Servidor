package com.haptikos.gestor_tareas_haptikos_servidor.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "homes")
public class Home {
    @Id
    private String id;

    private String name;

    @Column(nullable = true, length = 120)
    private String description;

    @Column(nullable = false)
    private boolean isPrivate = false;

    private String inviteCode;
    private Long createdAt = System.currentTimeMillis();

    // Configuración de notificaciones
    private boolean notifyTaskReminders = true;
    private boolean notifyTaskCompleted = true;
    private boolean notifyNewMembers = true;
    private boolean notifyAllMembers = true;
    private boolean forceSettings = false;

    // Relación Una casa tiene muchos miembros
    @OneToMany(mappedBy = "home", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Member> members;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public boolean isNotifyTaskCompleted() {
        return notifyTaskCompleted;
    }

    public void setNotifyTaskCompleted(boolean notifyTaskCompleted) {
        this.notifyTaskCompleted = notifyTaskCompleted;
    }

    public boolean isNotifyTaskReminders() {
        return notifyTaskReminders;
    }

    public void setNotifyTaskReminders(boolean notifyTaskReminders) {
        this.notifyTaskReminders = notifyTaskReminders;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isNotifyNewMembers() { return notifyNewMembers; }

    public void setNotifyNewMembers(boolean notifyNewMembers) { this.notifyNewMembers = notifyNewMembers; }

    public boolean isNotifyAllMembers() { return notifyAllMembers; }

    public void setNotifyAllMembers(boolean notifyAllMembers) { this.notifyAllMembers = notifyAllMembers; }

    public boolean isForceSettings() { return forceSettings; }

    public void setForceSettings(boolean forceSettings) { this.forceSettings = forceSettings; }
}
