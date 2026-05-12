package com.haptikos.gestor_tareas_haptikos_servidor.dto;

import java.util.List;

public class HomeSyncDto {
    public String id;
    public String name;
    public String description;
    public boolean isPrivate;
    public String inviteCode;
    public String editPermission;
    public boolean notifyTaskReminders;
    public boolean notifyTaskCompleted;
    public boolean notifyNewMembers;
    public boolean notifyAllMembers;
    public boolean forceSettings;
    public List<MemberSyncDto> members;
}

