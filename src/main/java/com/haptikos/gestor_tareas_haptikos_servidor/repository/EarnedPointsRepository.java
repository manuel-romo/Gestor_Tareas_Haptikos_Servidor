package com.haptikos.gestor_tareas_haptikos_servidor.repository;

import com.haptikos.gestor_tareas_haptikos_servidor.model.EarnedPoints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EarnedPointsRepository extends JpaRepository<EarnedPoints, String> {
    List<EarnedPoints> findByUserId(String userId);

    @Query("SELECT SUM(e.points) FROM EarnedPoints e WHERE e.userId = :userId")
    Integer getTotalByUserId(@Param("userId") String userId);
}