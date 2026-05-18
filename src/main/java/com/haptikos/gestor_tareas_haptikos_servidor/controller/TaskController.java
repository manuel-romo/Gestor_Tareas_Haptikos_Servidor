package com.haptikos.gestor_tareas_haptikos_servidor.controller;

import com.haptikos.gestor_tareas_haptikos_servidor.dto.*;
import com.haptikos.gestor_tareas_haptikos_servidor.model.Member;
import com.haptikos.gestor_tareas_haptikos_servidor.model.Task;
import com.haptikos.gestor_tareas_haptikos_servidor.repository.MemberRepository;
import com.haptikos.gestor_tareas_haptikos_servidor.repository.RoomRepository;
import com.haptikos.gestor_tareas_haptikos_servidor.repository.TaskRepository;
import com.haptikos.gestor_tareas_haptikos_servidor.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{taskId}")
    public ResponseEntity<Void> updateTask(
            @PathVariable String taskId,
            @RequestBody UpdateTaskRequest request) {
        try {
            taskService.updateTask(taskId, request);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/instances")
    public ResponseEntity<Void> createInstance(@RequestBody CreateTaskInstanceRequest request) {
        taskService.createInstance(request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/instances/{instanceId}/complete")
    public ResponseEntity<Void> completeInstance(
            @PathVariable String instanceId,
            @RequestParam String userId
    ) {
        taskService.completeInstance(instanceId, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/home/{homeId}")
    public ResponseEntity<List<TaskNetworkDto>> getTasksByHome(@PathVariable String homeId) {
        List<TaskNetworkDto> tasks = taskService.getTasksByHome(homeId);
        return ResponseEntity.ok(tasks);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable String taskId,
            @RequestParam String userId) {
        try {
            taskService.deleteTask(taskId, userId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}