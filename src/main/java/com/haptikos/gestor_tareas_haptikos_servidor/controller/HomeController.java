package com.haptikos.gestor_tareas_haptikos_servidor.controller;

import com.haptikos.gestor_tareas_haptikos_servidor.dto.*;
import com.haptikos.gestor_tareas_haptikos_servidor.model.Home;
import com.haptikos.gestor_tareas_haptikos_servidor.model.Member;
import com.haptikos.gestor_tareas_haptikos_servidor.service.HomeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/homes")
public class HomeController {

    private final HomeService homeService;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @PostMapping
    public ResponseEntity<?> createHome(@RequestBody CreateHomeRequest request) {
        try {
            Home createdHome = homeService.createHome(request);

            return ResponseEntity.ok(Map.of(
                    "message", "Hogar creado con éxito",
                    "inviteCode", createdHome.getInviteCode(),
                    "homeId", createdHome.getId()
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al crear el hogar: " + e.getMessage()));
        }
    }

    @PatchMapping("/{homeId}")
    public ResponseEntity<?> updateHome(
            @PathVariable String homeId,
            @RequestBody UpdateHomeRequest request) {
        try {
            Home updatedHome = homeService.updateHome(homeId, request);

            return ResponseEntity.ok(Map.of(
                    "message", "Hogar actualizado con éxito",
                    "homeId", updatedHome.getId()
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al actualizar el hogar: " + e.getMessage()));
        }
    }


    @DeleteMapping("/{homeId}")
    public ResponseEntity<?> deleteHome(@PathVariable String homeId) {
        try {
            homeService.deleteHome(homeId);
            return ResponseEntity.ok(Map.of("message", "Hogar eliminado con éxito"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/{homeId}/regenerate-code")
    public ResponseEntity<?> regenerateCode(@PathVariable String homeId) {
        try {
            Home home = homeService.regenerateInviteCode(homeId);
            return ResponseEntity.ok(Map.of("inviteCode", home.getInviteCode()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/by-code/{inviteCode}")
    public ResponseEntity<?> findByCode(
            @PathVariable String inviteCode,
            @RequestParam String userId) {
        try {
            HomePreviewDto preview = homeService.findHomeByCode(inviteCode, userId);
            return ResponseEntity.ok(preview);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/join")
    public ResponseEntity<?> joinHome(@RequestBody JoinHomeRequest request) {
        try {
            Member member = homeService.joinHome(
                    request.getInviteCode(), request.getUserId(),
                    request.getName(), request.getLastName(), request.getColorHex()
            );
            return ResponseEntity.ok(Map.of(
                    "message", "Te uniste con éxito",
                    "memberId", member.getId()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getHomesByUser(@PathVariable String userId) {
        try {
            List<HomeSyncDto> homes = homeService.getHomesByUserId(userId);
            return ResponseEntity.ok(homes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{homeId}/members/{userId}")
    public ResponseEntity<?> leaveHome(
            @PathVariable String homeId,
            @PathVariable String userId) {
        try {
            homeService.leaveHome(homeId, userId);

            return ResponseEntity.ok(Map.of("message", "Abandonaste el hogar con éxito"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }

}