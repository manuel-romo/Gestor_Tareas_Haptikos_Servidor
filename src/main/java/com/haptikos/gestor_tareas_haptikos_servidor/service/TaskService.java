package com.haptikos.gestor_tareas_haptikos_servidor.service;

import com.haptikos.gestor_tareas_haptikos_servidor.controller.TaskController;
import com.haptikos.gestor_tareas_haptikos_servidor.dto.CreateTaskInstanceRequest;
import com.haptikos.gestor_tareas_haptikos_servidor.dto.CreateTaskRequest;
import com.haptikos.gestor_tareas_haptikos_servidor.dto.TaskResponse;
import com.haptikos.gestor_tareas_haptikos_servidor.model.Home;
import com.haptikos.gestor_tareas_haptikos_servidor.model.Member;
import com.haptikos.gestor_tareas_haptikos_servidor.model.Task;
import com.haptikos.gestor_tareas_haptikos_servidor.model.TaskInstance;
import com.haptikos.gestor_tareas_haptikos_servidor.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final RoomRepository roomRepository;
    private final HomeRepository homeRepository;
    private final MemberRepository memberRepository;
    private final TaskInstanceRepository taskInstanceRepository;
    private final NotificationService notificationService;

    public TaskService(TaskRepository taskRepository,
                       RoomRepository roomRepository,
                       MemberRepository memberRepository,
                       TaskInstanceRepository taskInstanceRepository,
                       NotificationService notificationService,
                       HomeRepository homeRepository) {
        this.taskRepository = taskRepository;
        this.roomRepository = roomRepository;
        this.memberRepository = memberRepository;
        this.taskInstanceRepository = taskInstanceRepository;
        this.notificationService = notificationService;
        this.homeRepository = homeRepository;
    }

    @Transactional
    public TaskResponse createTask(CreateTaskRequest request) {
        Task task = new Task();
        task.setId(request.getId());
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPoints(request.getPoints());
        task.setPriority(request.getPriority());
        task.setSuggestedDay(request.getSuggestedDay());
        task.setRecurrence(request.getRecurrence());
        task.setWorkMode(request.getWorkMode());
        task.setLastMemberIndex(request.getLastMemberIndex());
        task.setHomeId(request.getHomeId());

        if (request.getRoomId() != null) {
            roomRepository.findById(request.getRoomId()).ifPresent(task::setRoom);
        }

        if (request.getMemberIds() != null && !request.getMemberIds().isEmpty()) {
            List<Member> assignedMembers = memberRepository.findAllById(request.getMemberIds());
            task.setMembers(assignedMembers);
        }

        Task savedTask = taskRepository.save(task);

        return new TaskResponse(savedTask.getId(), savedTask.getTitle());
    }

    @Transactional
    public void createInstance(CreateTaskInstanceRequest request) {
        TaskInstance instance = new TaskInstance();
        instance.setId(request.getId());
        instance.setDueDate(request.getDueDate());
        instance.setState(request.getState());

        taskRepository.findById(request.getTaskId())
                .ifPresent(instance::setTask);

        if (request.getMemberIds() != null && !request.getMemberIds().isEmpty()) {
            List<Member> assignedMembers = memberRepository.findAllById(request.getMemberIds());
            instance.setMembers(assignedMembers);
        }

        taskInstanceRepository.save(instance);
    }

    @Transactional
    public void completeInstance(String instanceId) {
        TaskInstance instance = taskInstanceRepository.findById(instanceId)
                .orElseThrow(() -> new RuntimeException("Instancia no encontrada"));

        instance.setState("COMPLETED");
        taskInstanceRepository.save(instance);

        Task task = instance.getTask();
        if (task == null) return;

        Home home = homeRepository.findById(task.getHomeId()).orElse(null);
        if (home == null) return;

        // Notificar a todos los miembros del hogar
        notificationService.notifyHomeMembers(
                home,
                "Tarea completada",
                "\"" + task.getTitle() + "\" fue marcada como completada",
                "TASK_COMPLETED"
        );
    }
}