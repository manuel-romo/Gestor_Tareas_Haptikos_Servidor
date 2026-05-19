package com.haptikos.gestor_tareas_haptikos_servidor.dto;

public class HomePreviewDto {
    public String id;
    public String name;
    public String creatorName;
    public int memberCount;
    public long taskCount;
    public long pendingCount;
    public boolean isAlreadyMember;

    public HomePreviewDto(String id, String name, String creatorName,
                          int memberCount, long taskCount, long pendingCount,
                          boolean isAlreadyMember) {
        this.id = id;
        this.name = name;
        this.creatorName = creatorName;
        this.memberCount = memberCount;
        this.taskCount = taskCount;
        this.pendingCount = pendingCount;
        this.isAlreadyMember = isAlreadyMember;
    }

}