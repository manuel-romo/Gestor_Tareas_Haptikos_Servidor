package com.haptikos.gestor_tareas_haptikos_servidor.controller;

import com.haptikos.gestor_tareas_haptikos_servidor.dto.ChallengeProgressDto;
import com.haptikos.gestor_tareas_haptikos_servidor.service.ChallengeProgressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/challenges")
public class ChallengeProgressController {

    private final ChallengeProgressService service;

    public ChallengeProgressController(ChallengeProgressService service) {
        this.service = service;
    }

    @PostMapping("/upsert")
    public ResponseEntity<Void> upsert(@RequestBody ChallengeProgressDto dto) {
        service.upsert(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/week")
    public ResponseEntity<List<ChallengeProgressDto>> getForWeek(
            @RequestParam String userId,
            @RequestParam String homeId,
            @RequestParam String weekId
    ) {
        return ResponseEntity.ok(service.getForWeek(userId, homeId, weekId));
    }


}