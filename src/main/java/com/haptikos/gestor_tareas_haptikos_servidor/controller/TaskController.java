package com.haptikos.gestor_tareas_haptikos_servidor.controller;

import com.haptikos.gestor_tareas_haptikos_servidor.dto.CreateTaskRequest;
import com.haptikos.gestor_tareas_haptikos_servidor.dto.TaskResponse;
import com.haptikos.gestor_tareas_haptikos_servidor.model.Member;
import com.haptikos.gestor_tareas_haptikos_servidor.model.Task;
import com.haptikos.gestor_tareas_haptikos_servidor.repository.MemberRepository;
import com.haptikos.gestor_tareas_haptikos_servidor.repository.RoomRepository;
import com.haptikos.gestor_tareas_haptikos_servidor.repository.TaskRepository;
import com.haptikos.gestor_tareas_haptikos_servidor.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody CreateTaskRequest request) {
        TaskResponse response = taskService.createTask(request);
        return ResponseEntity.ok(response);
    }
}