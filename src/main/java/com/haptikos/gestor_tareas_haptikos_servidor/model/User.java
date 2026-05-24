package com.haptikos.gestor_tareas_haptikos_servidor.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    @Id
    private String id;

    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    private String gender;
    private String dob;
    private String profilePicUrl;
    private boolean notifyTaskReminders = true;
    private boolean notifyTaskCompleted = true;
    private boolean notifyNewMembers = true;
    private String fcmToken;
    private String resetToken;
    private LocalDateTime resetTokenExpiry;

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getDob() { return dob; }
    public void setDob(String dob) { this.dob = dob; }

    public String getProfilePicUrl() { return profilePicUrl; }
    public void setProfilePicUrl(String profilePicUrl) { this.profilePicUrl = profilePicUrl; }

    public boolean isNotifyTaskReminders() { return notifyTaskReminders; }

    public void setNotifyTaskReminders(boolean notifyTaskReminders) { this.notifyTaskReminders = notifyTaskReminders; }

    public boolean isNotifyTaskCompleted() { return notifyTaskCompleted; }

    public void setNotifyTaskCompleted(boolean notifyTaskCompleted) { this.notifyTaskCompleted = notifyTaskCompleted; }

    public boolean isNotifyNewMembers() { return notifyNewMembers; }

    public void setNotifyNewMembers(boolean notifyNewMembers) { this.notifyNewMembers = notifyNewMembers; }

    public String getFcmToken() { return fcmToken; }

    public void setFcmToken(String fcmToken) { this.fcmToken = fcmToken; }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public LocalDateTime getResetTokenExpiry() {
        return resetTokenExpiry;
    }

    public void setResetTokenExpiry(LocalDateTime resetTokenExpiry) {
        this.resetTokenExpiry = resetTokenExpiry;
    }
}