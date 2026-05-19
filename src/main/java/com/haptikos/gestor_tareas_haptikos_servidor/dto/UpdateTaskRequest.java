package com.haptikos.gestor_tareas_haptikos_servidor.dto;

import java.util.List;

public class UpdateTaskRequest {
    private String title;
    private String description;
    private int points;
    private String priority;
    private String suggestedDay;
    private String recurrence;
    private String workMode;
    private String roomId;
    private List<String> memberIds;
    private Long pausedUntil;

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
    public String getRoomId() { return roomId; }
    public void setRoomId(String roomId) { this.roomId = roomId; }
    public List<String> getMemberIds() { return memberIds; }
    public void setMemberIds(List<String> memberIds) { this.memberIds = memberIds; }
    public Long getPausedUntil() { return pausedUntil; }
    public void setPausedUntil(Long pausedUntil) { this.pausedUntil = pausedUntil; }
}