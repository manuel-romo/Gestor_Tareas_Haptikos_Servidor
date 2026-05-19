package com.haptikos.gestor_tareas_haptikos_servidor.controller;

import com.haptikos.gestor_tareas_haptikos_servidor.dto.InviteEmailRequest;
import com.haptikos.gestor_tareas_haptikos_servidor.service.HomeInviteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/homes")
public class HomeInviteController {

    private final HomeInviteService homeInviteService;

    @Autowired
    public HomeInviteController(HomeInviteService homeInviteService) {
        this.homeInviteService = homeInviteService;
    }

    @PostMapping("/{homeId}/invite-email")
    public ResponseEntity<Map<String, String>> inviteByEmail(
            @PathVariable String homeId,
            @RequestBody InviteEmailRequest request) {
        try {
            homeInviteService.sendInviteEmail(
                    request.getEmail(),
                    request.getHomeName(),
                    request.getInviteCode()
            );
            Map<String, String> response = new HashMap<>();
            response.put("status", "sent");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "No se pudo enviar el correo");
            return ResponseEntity.status(500).body(error);
        }
    }
}