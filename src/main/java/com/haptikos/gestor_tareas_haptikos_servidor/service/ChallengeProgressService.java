package com.haptikos.gestor_tareas_haptikos_servidor.service;

import com.haptikos.gestor_tareas_haptikos_servidor.dto.ChallengeProgressDto;
import com.haptikos.gestor_tareas_haptikos_servidor.model.ChallengeProgress;
import com.haptikos.gestor_tareas_haptikos_servidor.repository.ChallengeProgressRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChallengeProgressService {

    private final ChallengeProgressRepository repository;

    public ChallengeProgressService(ChallengeProgressRepository challengeProgressRepository){
        this.repository = challengeProgressRepository;
    }

    public void upsert(ChallengeProgressDto dto) {
        var existing = repository.findByUserIdAndHomeIdAndChallengeTypeAndWeekId(
                dto.getUserId(), dto.getHomeId(), dto.getChallengeType(), dto.getWeekId()
        );

        ChallengeProgress entity = existing.orElse(new ChallengeProgress());
        entity.setId(dto.getId());
        entity.setUserId(dto.getUserId());
        entity.setHomeId(dto.getHomeId());
        entity.setChallengeType(dto.getChallengeType());
        entity.setWeekId(dto.getWeekId());
        entity.setCurrentProgress(dto.getCurrentProgress());
        entity.setCompleted(dto.isCompleted());
        // ✅ No sobreescribir pointsAwarded si ya era true
        entity.setPointsAwarded(
                existing.map(ChallengeProgress::isPointsAwarded).orElse(false) || dto.isPointsAwarded()
        );
        entity.setCompletedAt(dto.getCompletedAt());
        repository.save(entity);
    }

    public List<ChallengeProgressDto> getForWeek(String userId, String homeId, String weekId) {
        return repository.findByUserIdAndHomeIdAndWeekId(userId, homeId, weekId)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    private ChallengeProgressDto toDto(ChallengeProgress e) {
        ChallengeProgressDto dto = new ChallengeProgressDto();
        dto.setId(e.getId());
        dto.setUserId(e.getUserId());
        dto.setHomeId(e.getHomeId());
        dto.setChallengeType(e.getChallengeType());
        dto.setWeekId(e.getWeekId());
        dto.setCurrentProgress(e.getCurrentProgress());
        dto.setCompleted(e.isCompleted());
        dto.setPointsAwarded(e.isPointsAwarded());
        dto.setCompletedAt(e.getCompletedAt());
        return dto;
    }
}