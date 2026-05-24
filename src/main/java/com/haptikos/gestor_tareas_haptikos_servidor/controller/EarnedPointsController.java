package com.haptikos.gestor_tareas_haptikos_servidor.controller;

import com.haptikos.gestor_tareas_haptikos_servidor.dto.EarnedPointsDto;
import com.haptikos.gestor_tareas_haptikos_servidor.model.EarnedPoints;
import com.haptikos.gestor_tareas_haptikos_servidor.repository.EarnedPointsRepository;
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