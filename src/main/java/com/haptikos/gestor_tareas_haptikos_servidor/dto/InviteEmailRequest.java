package com.haptikos.gestor_tareas_haptikos_servidor.dto;

public class InviteEmailRequest {
    private String email;
    private String homeName;
    private String inviteCode;

    public InviteEmailRequest() {}

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getHomeName() { return homeName; }
    public void setHomeName(String homeName) { this.homeName = homeName; }

    public String getInviteCode() { return inviteCode; }
    public void setInviteCode(String inviteCode) { this.inviteCode = inviteCode; }
}