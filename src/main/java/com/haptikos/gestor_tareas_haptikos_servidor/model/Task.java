package com.haptikos.gestor_tareas_haptikos_servidor.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    private String id;
    private String title;
    private String description;
    private int points;
    private String priority;
    private String suggestedDay;
    private String recurrence;
    private String workMode;
    private int lastMemberIndex;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToMany
    @JoinTable(
            name = "task_member_join",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id")
    )
    private List<Member> members = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getSuggestedDay() {
        return suggestedDay;
    }

    public void setSuggestedDay(String suggestedDay) {
        this.suggestedDay = suggestedDay;
    }

    public String getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(String recurrence) {
        this.recurrence = recurrence;
    }

    public int getLastMemberIndex() {
        return lastMemberIndex;
    }

    public void setLastMemberIndex(int lastMemberIndex) {
        this.lastMemberIndex = lastMemberIndex;
    }

    public String getWorkMode() {
        return workMode;
    }

    public void setWorkMode(String workMode) {
        this.workMode = workMode;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
}