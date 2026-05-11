package com.haptikos.gestor_tareas_haptikos_servidor.controller;

import com.haptikos.gestor_tareas_haptikos_servidor.dto.CreateHomeRequest;
import com.haptikos.gestor_tareas_haptikos_servidor.dto.UpdateHomeRequest;
import com.haptikos.gestor_tareas_haptikos_servidor.model.Home;
import com.haptikos.gestor_tareas_haptikos_servidor.service.HomeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    /**
    @DeleteMapping("/{homeId}")
    public ResponseEntity<?> deleteHome(@PathVariable String homeId) {
        try {
            homeService.deleteHome(homeId);
            return ResponseEntity.ok(Map.of("message", "Hogar eliminado con éxito"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }
    */

}