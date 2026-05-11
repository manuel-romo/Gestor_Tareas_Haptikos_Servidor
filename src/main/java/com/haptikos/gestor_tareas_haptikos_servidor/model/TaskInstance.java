package com.haptikos.gestor_tareas_haptikos_servidor.model;

import jakarta.persistence.*;

@Entity
@Table(name = "task_instances")
public class TaskInstance {
    @Id
    private String id;

    private Long dueDate;
    private String state;
    private Long pausedUntil;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Long getPausedUntil() {
        return pausedUntil;
    }

    public void setPausedUntil(Long pausedUntil) {
        this.pausedUntil = pausedUntil;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getDueDate() {
        return dueDate;
    }

    public void setDueDate(Long dueDate) {
        this.dueDate = dueDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}