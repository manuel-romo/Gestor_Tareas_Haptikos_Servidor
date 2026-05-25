package com.haptikos.gestor_tareas_haptikos_servidor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "earned_points")
public class EarnedPoints {
    @Id
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


