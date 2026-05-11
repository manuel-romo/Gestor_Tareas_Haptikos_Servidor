package com.haptikos.gestor_tareas_haptikos_servidor.service;

import com.haptikos.gestor_tareas_haptikos_servidor.dto.CreateTaskRequest;
import com.haptikos.gestor_tareas_haptikos_servidor.dto.TaskResponse;
import com.haptikos.gestor_tareas_haptikos_servidor.model.Member;
import com.haptikos.gestor_tareas_haptikos_servidor.model.Task;
import com.haptikos.gestor_tareas_haptikos_servidor.repository.MemberRepository;
import com.haptikos.gestor_tareas_haptikos_servidor.repository.RoomRepository;
import com.haptikos.gestor_tareas_haptikos_servidor.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;

    public TaskService(TaskRepository taskRepository,
                       RoomRepository roomRepository,
                       MemberRepository memberRepository) {
        this.taskRepository = taskRepository;
        this.roomRepository = roomRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public TaskResponse createTask(CreateTaskRequest request) {
        Task task = new Task();
        task.setId(request.id);
        task.setTitle(request.title);
        task.setDescription(request.description);
        task.setPoints(request.points);
        task.setPriority(request.priority);
        task.setSuggestedDay(request.suggestedDay);
        task.setRecurrence(request.recurrence);
        task.setWorkMode(request.workMode);
        task.setLastMemberIndex(request.lastMemberIndex);

        if (request.roomId != null) {
            roomRepository.findById(request.roomId).ifPresent(task::setRoom);
        }

        if (request.memberIds != null && !request.memberIds.isEmpty()) {
            List<Member> assignedMembers = memberRepository.findAllById(request.memberIds);
            task.setMembers(assignedMembers);
        }

        Task savedTask = taskRepository.save(task);

        return new TaskResponse(savedTask.getId(), savedTask.getTitle());
    }
}