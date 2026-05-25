package com.haptikos.gestor_tareas_haptikos_servidor.repository;

import com.haptikos.gestor_tareas_haptikos_servidor.model.ChallengeProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ChallengeProgressRepository extends JpaRepository<ChallengeProgress, String> {
    List<ChallengeProgress> findByUserIdAndHomeIdAndWeekId(String userId, String homeId, String weekId);
    Optional<ChallengeProgress> findByUserIdAndHomeIdAndChallengeTypeAndWeekId(
            String userId, String homeId, String challengeType, String weekId
    );
}