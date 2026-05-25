package com.haptikos.gestor_tareas_haptikos_servidor.controller;

import com.haptikos.gestor_tareas_haptikos_servidor.dto.EarnedPointsDto;
import com.haptikos.gestor_tareas_haptikos_servidor.model.EarnedPoints;
import com.haptikos.gestor_tareas_haptikos_servidor.model.Home;
import com.haptikos.gestor_tareas_haptikos_servidor.repository.EarnedPointsRepository;
import com.haptikos.gestor_tareas_haptikos_servidor.repository.HomeRepository;
import com.haptikos.gestor_tareas_haptikos_servidor.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/earned-points")
public class EarnedPointsController {

    @Autowired
    private EarnedPointsRepository repo;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private HomeRepository homeRepository;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody EarnedPointsDto dto) {
        if (repo.existsById(dto.getInstanceId())) {
            return ResponseEntity.ok("already_exists");
        }

        EarnedPoints entity = new EarnedPoints();
        entity.setInstanceId(dto.getInstanceId());
        entity.setUserId(dto.getUserId());
        entity.setPoints(dto.getPoints());
        entity.setEarnedAt(dto.getEarnedAt());
        repo.save(entity);

        // Notificar a los demás miembros del hogar
        //Home home = homeRepository.findByMembersUserId(dto.getUserId());
        //if (home != null) {
            //notificationService.sendSilentSyncToHome(home, "SYNC_EARNED_POINTS", dto.getUserId());
        //}

        return ResponseEntity.ok("saved");
    }

    @GetMapping("/user/{userId}")
    public List<EarnedPointsDto> getByUser(@PathVariable String userId) {
        return repo.findByUserId(userId).stream().map(e -> {
            EarnedPointsDto dto = new EarnedPointsDto();
            dto.setInstanceId(e.getInstanceId());
            dto.setUserId(e.getUserId());
            dto.setPoints(e.getPoints());
            dto.setEarnedAt(e.getEarnedAt());
            return dto;
        }).collect(Collectors.toList());
    }
}