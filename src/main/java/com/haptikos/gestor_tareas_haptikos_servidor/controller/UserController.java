package com.haptikos.gestor_tareas_haptikos_servidor.controller;

import com.haptikos.gestor_tareas_haptikos_servidor.dto.HomeSyncDto;
import com.haptikos.gestor_tareas_haptikos_servidor.dto.UpdateUserRequest;
import com.haptikos.gestor_tareas_haptikos_servidor.model.Home;
import com.haptikos.gestor_tareas_haptikos_servidor.model.Member;
import com.haptikos.gestor_tareas_haptikos_servidor.model.User;
import com.haptikos.gestor_tareas_haptikos_servidor.repository.HomeRepository;
import com.haptikos.gestor_tareas_haptikos_servidor.repository.MemberRepository;
import com.haptikos.gestor_tareas_haptikos_servidor.repository.UserRepository;
import com.haptikos.gestor_tareas_haptikos_servidor.service.FileService;
import com.haptikos.gestor_tareas_haptikos_servidor.service.HomeService;
import com.haptikos.gestor_tareas_haptikos_servidor.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final FileService fileService;
    private final MemberRepository memberRepository;
    private final UserRepository userRepository;
    private final HomeRepository homeRepository;
    private final NotificationService notificationService;

    public UserController(
            FileService fileService,
            UserRepository userRepository,
            MemberRepository memberRepository,
            HomeRepository homeRepository,
            NotificationService notificationService) {
        this.fileService = fileService;
        this.userRepository = userRepository;
        this.memberRepository = memberRepository;
        this.homeRepository = homeRepository;
        this.notificationService = notificationService;
    }

    @PostMapping("/{userId}/profile-picture")
    public ResponseEntity<Map<String, String>> uploadProfilePicture(
            @PathVariable String userId,
            @RequestParam("file") MultipartFile file) {

        try {
            // Se busca al usuario
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            // Subida de foto
            String secureUrl = fileService.uploadImage(file);

            // Se actualiza la base de datos
            User user = userOptional.get();
            user.setProfilePicUrl(secureUrl);
            userRepository.save(user);

            memberRepository.findByUserId(userId).forEach(member -> {
                member.setProfilePicUrl(secureUrl);
                memberRepository.save(member);
            });

            List<Member> members = memberRepository.findByUserId(userId);
            for (Member member : members) {
                Home home = member.getHome();
                if (home != null) {
                    notificationService.sendSilentSyncToHome(home, "SYNC_MEMBERS", "");
                }
            }

            // Se devuelve la url
            Map<String, String> response = new HashMap<>();
            response.put("profilePicUrl", secureUrl);

            return ResponseEntity.ok(response);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Map<String, String>> updateUser(
            @PathVariable String userId,
            @RequestBody UpdateUserRequest request) {

        try {
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isEmpty()) return ResponseEntity.notFound().build();

            User user = userOptional.get();

            boolean nameChanged = false;
            if (request.getName() != null && !request.getName().trim().isEmpty()) {
                user.setName(request.getName());
                nameChanged = true;
            }
            if (request.getNotifyTaskReminders() != null) {
                user.setNotifyTaskReminders(request.getNotifyTaskReminders());
            }
            if (request.getNotifyTaskCompleted() != null) {
                user.setNotifyTaskCompleted(request.getNotifyTaskCompleted());
            }
            if (request.getNotifyNewMembers() != null) {
                user.setNotifyNewMembers(request.getNotifyNewMembers());
            }

            userRepository.save(user);

            if (request.getHomeId() != null && (
                    request.getNotifyTaskReminders() != null ||
                            request.getNotifyTaskCompleted() != null ||
                            request.getNotifyNewMembers() != null)) {

                memberRepository.findByUserIdAndHomeId(userId, request.getHomeId())
                        .ifPresent(member -> {
                            if (request.getNotifyTaskReminders() != null)
                                member.setNotifyTaskReminders(request.getNotifyTaskReminders());
                            if (request.getNotifyTaskCompleted() != null)
                                member.setNotifyTaskCompleted(request.getNotifyTaskCompleted());
                            if (request.getNotifyNewMembers() != null)
                                member.setNotifyNewMembers(request.getNotifyNewMembers());
                            memberRepository.save(member);
                        });
            }


            if (nameChanged) {
                List<Member> members = memberRepository.findByUserId(userId);
                for (Member member : members) {
                    member.setName(request.getName());
                    memberRepository.save(member);

                    Home home = member.getHome();
                    if (home != null) {
                        notificationService.sendSilentSyncToHome(home, "SYNC_MEMBERS", "");
                    }
                }
            }

            Map<String, String> response = new HashMap<>();
            response.put("message", "Usuario actualizado correctamente");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/{userId}/fcm-token")
    public ResponseEntity<Void> updateFcmToken(
            @PathVariable String userId,
            @RequestBody Map<String, String> body) {
        String token = body.get("fcmToken");
        userRepository.findById(userId).ifPresent(user -> {
            user.setFcmToken(token);
            userRepository.save(user);
        });
        return ResponseEntity.ok().build();
    }

}