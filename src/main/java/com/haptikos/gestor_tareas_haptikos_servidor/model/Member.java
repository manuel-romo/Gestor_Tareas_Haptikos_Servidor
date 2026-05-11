package com.haptikos.gestor_tareas_haptikos_servidor.model;

import jakarta.persistence.*;

@Entity
@Table(name = "members")
public class Member {
    @Id
    private String id;

    private String name;
    private String lastName;
    private String colorHex;
    private String role;
    private String status;

    @ManyToOne
    @JoinColumn(name = "home_id")
    private Home home;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
}