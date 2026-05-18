package com.haptikos.gestor_tareas_haptikos_servidor.service;

import com.haptikos.gestor_tareas_haptikos_servidor.controller.TaskController;
import com.haptikos.gestor_tareas_haptikos_servidor.dto.*;
import com.haptikos.gestor_tareas_haptikos_servidor.model.Home;
import com.haptikos.gestor_tareas_haptikos_servidor.model.Member;
import com.haptikos.gestor_tareas_haptikos_servidor.model.Task;
import com.haptikos.gestor_tareas_haptikos_servidor.model.TaskInstance;
import com.haptikos.gestor_tareas_haptikos_servidor.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        task.setPredetermined(request.isPredetermined());

        if (request.getRoomId() != null) {
            roomRepository.findById(request.getRoomId()).ifPresent(task::setRoom);
        }

        if (request.getMemberIds() != null && !request.getMemberIds().isEmpty()) {
            List<Member> assignedMembers = memberRepository.findAllById(request.getMemberIds());
            task.setMembers(assignedMembers);
        }

        Task savedTask = taskRepository.save(task);

        homeRepository.findById(request.getHomeId()).ifPresent(home -> {
            notificationService.sendSilentSyncToHome(home, "SYNC_TASKS", request.getUserId() != null ? request.getUserId() : "");
        });

        return new TaskResponse(savedTask.getId(), savedTask.getTitle());
    }

    @Transactional
    public void updateTask(String taskId, UpdateTaskRequest request) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPoints(request.getPoints());
        task.setPriority(request.getPriority());
        task.setSuggestedDay(request.getSuggestedDay());
        task.setRecurrence(request.getRecurrence());
        task.setWorkMode(request.getWorkMode());
        task.setPausedUntil(request.getPausedUntil());

        if (request.getRoomId() != null) {
            roomRepository.findById(request.getRoomId()).ifPresent(task::setRoom);
        } else {
            task.setRoom(null);
        }

        if (request.getMemberIds() != null) {
            List<Member> members = memberRepository.findAllById(request.getMemberIds());
            task.setMembers(members);
        }

        taskRepository.save(task);

        homeRepository.findById(task.getHomeId()).ifPresent(home ->
                notificationService.sendSilentSyncToHome(home, "SYNC_TASKS", "")
        );
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

        taskRepository.findById(request.getTaskId()).ifPresent(task -> {
            homeRepository.findById(task.getHomeId()).ifPresent(home -> {
                notificationService.sendSilentSyncToHome(home, "SYNC_TASKS",
                        request.getUserId() != null ? request.getUserId() : "");
            });
        });
    }

    @Transactional
    public void completeInstance(String instanceId, String userId) {
        TaskInstance instance = taskInstanceRepository.findById(instanceId)
                .orElseThrow(() -> new RuntimeException("Instancia no encontrada"));

        instance.setState("COMPLETED");
        taskInstanceRepository.save(instance);

        Task task = instance.getTask();
        if (task == null) return;

        Home home = homeRepository.findById(task.getHomeId()).orElse(null);
        if (home == null) return;

        notificationService.sendSilentSyncToHome(home, "SYNC_TASKS", "");

        notificationService.notifyHomeMembers(
                home,
                "Tarea completada",
                "\"" + task.getTitle() + "\" fue marcada como completada",
                "TASK_COMPLETED",
                userId
        );
    }


    public List<TaskNetworkDto> getTasksByHome(String homeId) {
        List<Task> tasks = taskRepository.findByHomeId(homeId);

        // Una sola query para todas las instancias del hogar
        List<TaskInstance> allInstances = taskInstanceRepository.findByTask_HomeId(homeId);
        Map<String, List<TaskInstance>> instancesByTaskId = allInstances.stream()
                .collect(Collectors.groupingBy(i -> i.getTask().getId()));

        return tasks.stream().map(task -> {
            TaskNetworkDto dto = new TaskNetworkDto();
            dto.setId(task.getId());
            dto.setTitle(task.getTitle());
            dto.setDescription(task.getDescription());
            dto.setPoints(task.getPoints());
            dto.setPriority(task.getPriority());
            dto.setSuggestedDay(task.getSuggestedDay());
            dto.setRecurrence(task.getRecurrence());
            dto.setWorkMode(task.getWorkMode());
            dto.setLastMemberIndex(task.getLastMemberIndex());
            dto.setRoomId(task.getRoom() != null ? task.getRoom().getId() : null);
            dto.setHomeId(task.getHomeId());
            dto.setPredetermined(Boolean.TRUE.equals(task.getPredetermined()));
            dto.setMemberIds(task.getMembers().stream()
                    .map(Member::getId)
                    .collect(Collectors.toList()));

            List<TaskInstanceNetworkDto> instanceDtos = instancesByTaskId
                    .getOrDefault(task.getId(), List.of())
                    .stream()
                    .map(inst -> {
                        TaskInstanceNetworkDto iDto = new TaskInstanceNetworkDto();
                        iDto.setId(inst.getId());
                        iDto.setDueDate(inst.getDueDate());
                        iDto.setState(inst.getState());
                        iDto.setTaskId(task.getId());
                        iDto.setMemberIds(inst.getMembers().stream()
                                .map(Member::getId)
                                .collect(Collectors.toList()));
                        return iDto;
                    }).collect(Collectors.toList());

            dto.setInstances(instanceDtos);
            return dto;
        }).collect(Collectors.toList());
    }

    @Transactional
    public void deleteTask(String taskId, String userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        String homeId = task.getHomeId();

        taskInstanceRepository.deleteByTaskId(taskId);

        taskRepository.delete(task);

        homeRepository.findById(homeId).ifPresent(home ->
                notificationService.sendSilentSyncToHome(home, "SYNC_TASKS", userId)
        );
    }

}