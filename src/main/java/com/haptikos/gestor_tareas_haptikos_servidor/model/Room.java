package com.haptikos.gestor_tareas_haptikos_servidor.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "rooms")
public class Room {
    @Id
    private String id;

    private String name;
    private String icon;
    private String colorHex;

    @ManyToOne
    @JoinColumn(name = "home_id")
    private Home home;

    @OneToMany(mappedBy = "room")
    private List<Task> tasks;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getColorHex() {
        return colorHex;
    }

    public void setColorHex(String colorHex) {
        this.colorHex = colorHex;
    }

    public Home getHome() {
        return home;
    }

    public void setHome(Home home) {
        this.home = home;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}