package com.haptikos.gestor_tareas_haptikos_servidor.components;

import com.haptikos.gestor_tareas_haptikos_servidor.model.Home;
import com.haptikos.gestor_tareas_haptikos_servidor.model.Member;
import com.haptikos.gestor_tareas_haptikos_servidor.model.Task;
import com.haptikos.gestor_tareas_haptikos_servidor.model.TaskInstance;
import com.haptikos.gestor_tareas_haptikos_servidor.repository.HomeRepository;
import com.haptikos.gestor_tareas_haptikos_servidor.repository.TaskInstanceRepository;
import com.haptikos.gestor_tareas_haptikos_servidor.service.NotificationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReminderScheduler {

    private final TaskInstanceRepository taskInstanceRepository;
    private final NotificationService notificationService;
    private final HomeRepository homeRepository;

    public ReminderScheduler(TaskInstanceRepository taskInstanceRepository,
                             NotificationService notificationService,
                             HomeRepository homeRepository) {
        this.taskInstanceRepository = taskInstanceRepository;
        this.notificationService = notificationService;
        this.homeRepository = homeRepository;
    }

    // Cada hora
    @Scheduled(fixedRate = 3600000)
    public void sendReminders() {
        long now = System.currentTimeMillis();
        long in24h = now + (24 * 60 * 60 * 1000);

        List<TaskInstance> upcoming = taskInstanceRepository
                .findByStateAndDueDateBetween("PENDING", now, in24h);

        for (TaskInstance instance : upcoming) {
            Task task = instance.getTask();
            if (task == null) continue;

            Home home = homeRepository.findById(task.getHomeId()).orElse(null);
            if (home == null) continue;

            List<Member> assignedMembers = instance.getMembers();
            if (assignedMembers == null || assignedMembers.isEmpty()) continue;

            for (Member member : assignedMembers) {
                notificationService.sendNotificationToMember(
                        member,
                        home,
                        "Recordatorio de tarea",
                        "\"" + task.getTitle() + "\" vence pronto",
                        "TASK_REMINDER"
                );
            }
        }
    }
}