package com.haptikos.gestor_tareas_haptikos_servidor.dto;

import java.util.List;

public class TaskNetworkDto {
    private String id;
    private String title;
    private String description;
    private int points;
    private String priority;
    private String suggestedDay;
    private String recurrence;
    private String workMode;
    private int lastMemberIndex;
    private String roomId;
    private String homeId;
    private boolean isPredetermined;
    private List<String> memberIds;
    private List<TaskInstanceNetworkDto> instances;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }
    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }
    public String getSuggestedDay() { return suggestedDay; }
    public void setSuggestedDay(String suggestedDay) { this.suggestedDay = suggestedDay; }
    public String getRecurrence() { return recurrence; }
    public void setRecurrence(String recurrence) { this.recurrence = recurrence; }
    public String getWorkMode() { return workMode; }
    public void setWorkMode(String workMode) { this.workMode = workMode; }
    public int getLastMemberIndex() { return lastMemberIndex; }
    public void setLastMemberIndex(int lastMemberIndex) { this.lastMemberIndex = lastMemberIndex; }
    public String getRoomId() { return roomId; }
    public void setRoomId(String roomId) { this.roomId = roomId; }
    public String getHomeId() { return homeId; }
    public void setHomeId(String homeId) { this.homeId = homeId; }
    public boolean isPredetermined() { return isPredetermined; }
    public void setPredetermined(boolean predetermined) { isPredetermined = predetermined; }
    public List<String> getMemberIds() { return memberIds; }
    public void setMemberIds(List<String> memberIds) { this.memberIds = memberIds; }

    public List<TaskInstanceNetworkDto> getInstances() {
        return instances;
    }

    public void setInstances(List<TaskInstanceNetworkDto> instances) {
        this.instances = instances;
    }
}