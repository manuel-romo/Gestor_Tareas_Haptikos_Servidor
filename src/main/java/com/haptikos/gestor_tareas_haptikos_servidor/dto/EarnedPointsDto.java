package com.haptikos.gestor_tareas_haptikos_servidor.dto;

public class EarnedPointsDto {
    private String instanceId;
    private String userId;
    private int points;
    private Long earnedAt;

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Long getEarnedAt() {
        return earnedAt;
    }

    public void setEarnedAt(Long earnedAt) {
        this.earnedAt = earnedAt;
    }
}