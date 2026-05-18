package com.haptikos.gestor_tareas_haptikos_servidor.controller;

import com.haptikos.gestor_tareas_haptikos_servidor.dto.CreateMemberRequest;
import com.haptikos.gestor_tareas_haptikos_servidor.dto.MemberDto;
import com.haptikos.gestor_tareas_haptikos_servidor.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<?> createMember(@RequestBody CreateMemberRequest request) {
        try {
            // Guardado de miembro
            memberService.createMember(request);

            return ResponseEntity.ok(Map.of(
                    "message", "Miembro creado con éxito"
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al crear el miembro: " + e.getMessage()));
        }
    }

    @GetMapping("/home/{homeId}")
    public ResponseEntity<?> getMembersByHome(@PathVariable String homeId) {
        try {
            // Se buscan todos los miembros del hogar
            List<MemberDto> members = memberService.getMembersByHomeId(homeId);

            return ResponseEntity.ok(members);
        } catch (Exception e) {
            return ResponseEntity.status(404)
                    .body(Map.of("error", "Error al obtener miembros: " + e.getMessage()));
        }
    }
}