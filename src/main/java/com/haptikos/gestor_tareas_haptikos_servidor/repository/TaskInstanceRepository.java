package com.haptikos.gestor_tareas_haptikos_servidor.repository;

import com.haptikos.gestor_tareas_haptikos_servidor.model.TaskInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskInstanceRepository extends JpaRepository<TaskInstance, String> {

    @Query("SELECT COUNT(ti) FROM TaskInstance ti WHERE ti.task.room.home.id = :homeId AND ti.state = :state")
    long countByHomeIdAndState(@Param("homeId") String homeId, @Param("state") String state);

    List<TaskInstance> findByStateAndDueDateBetween(String state, long from, long to);

    List<TaskInstance> findByTask_HomeId(String homeId);

    void deleteByTaskId(String taskId);

}