package com.haptikos.gestor_tareas_haptikos_servidor.dto;

import java.util.List;

public class CreateHomeRequest {
    private String id;
    private String name;
    private String description;
    private boolean isPrivate;
    private String creatorId;
    private String creatorName;
    private String creatorLastName;
    private String creatorColorHex;
    private List<InvitedUserDto> invitedUsers;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getCreatorLastName() {
        return creatorLastName;
    }

    public void setCreatorLastName(String creatorLastName) {
        this.creatorLastName = creatorLastName;
    }

    public String getCreatorColorHex() {
        return creatorColorHex;
    }

    public void setCreatorColorHex(String creatorColorHex) {
        this.creatorColorHex = creatorColorHex;
    }

    public List<InvitedUserDto> getInvitedUsers() {
        return invitedUsers;
    }

    public void setInvitedUsers(List<InvitedUserDto> invitedUsers) {
        this.invitedUsers = invitedUsers;
    }
}
