package com.haptikos.gestor_tareas_haptikos_servidor.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.haptikos.gestor_tareas_haptikos_servidor.model.Home;
import com.haptikos.gestor_tareas_haptikos_servidor.model.Member;
import com.haptikos.gestor_tareas_haptikos_servidor.model.Notification;
import com.haptikos.gestor_tareas_haptikos_servidor.model.User;
import com.haptikos.gestor_tareas_haptikos_servidor.repository.NotificationRepository;
import com.haptikos.gestor_tareas_haptikos_servidor.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationService(NotificationRepository notificationRepository,
                               UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    public void sendNotificationToUser(String userId, String title, String body, String type, String homeId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null || user.getFcmToken() == null) return;

        // Guardar en base de datos
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setHomeId(homeId);
        notification.setTitle(title);
        notification.setBody(body);
        notification.setType(type);
        notificationRepository.save(notification);

        // Enviar push
        Message message = Message.builder()
                .setToken(user.getFcmToken())
                .setNotification(
                        com.google.firebase.messaging.Notification.builder()
                                .setTitle(title)
                                .setBody(body)
                                .build()
                )
                .putData("type", type)
                .putData("homeId", homeId)
                .build();

        try {
            FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendNotificationToMembers(List<String> userIds, String title, String body, String type, String homeId) {
        userIds.forEach(userId -> sendNotificationToUser(userId, title, body, type, homeId));
    }

    public List<Notification> getNotificationsForUser(String userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public void markAsRead(String notificationId) {
        notificationRepository.findById(notificationId).ifPresent(n -> {
            n.setRead(true);
            notificationRepository.save(n);
        });
    }

    public void notifyHomeMembers(Home home, String title, String body, String type) {
        for (Member member : home.getMembers()) {
            sendNotificationToMember(member, home, title, body, type);
        }
    }

    public void sendNotificationToMember(Member member, Home home, String title, String body, String type) {
        if (!shouldNotify(member, home, type)) return;

        User user = userRepository.findById(member.getUserId()).orElse(null);
        if (user == null || user.getFcmToken() == null) return;

        // Guardar en base de datos
        Notification notification = new Notification();
        notification.setUserId(user.getId());
        notification.setHomeId(home.getId());
        notification.setTitle(title);
        notification.setBody(body);
        notification.setType(type);
        notificationRepository.save(notification);

        // Enviar push
        Message message = Message.builder()
                .setToken(user.getFcmToken())
                .setNotification(
                        com.google.firebase.messaging.Notification.builder()
                                .setTitle(title)
                                .setBody(body)
                                .build()
                )
                .putData("type", type)
                .putData("homeId", home.getId())
                .build();

        try {
            FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
    }


    private boolean shouldNotify(Member member, Home home, String type) {
        // Si forceSettings es true, usa configuración del hogar
        if (home.isForceSettings()) {
            return switch (type) {
                case "TASK_COMPLETED" -> home.isNotifyTaskCompleted();
                case "TASK_REMINDER" -> home.isNotifyTaskReminders();
                case "NEW_MEMBER" -> home.isNotifyNewMembers();
                default -> false;
            };
        }

        // Si no, usa configuración del miembro
        return switch (type) {
            case "TASK_COMPLETED" -> member.isNotifyTaskCompleted();
            case "TASK_REMINDER" -> member.isNotifyTaskReminders();
            case "NEW_MEMBER" -> member.isNotifyNewMembers();
            default -> false;
        };
    }
}
