package com.haptikos.gestor_tareas_haptikos_servidor.service;

import com.haptikos.gestor_tareas_haptikos_servidor.dto.*;
import com.haptikos.gestor_tareas_haptikos_servidor.model.Home;
import com.haptikos.gestor_tareas_haptikos_servidor.model.Member;
import com.haptikos.gestor_tareas_haptikos_servidor.model.User;
import com.haptikos.gestor_tareas_haptikos_servidor.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class HomeService {

    private final HomeRepository homeRepository;
    private final TaskRepository taskRepository;
    private final MemberRepository memberRepository;
    private final TaskInstanceRepository taskInstanceRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public HomeService(HomeRepository homeRepository, TaskRepository taskRepository,
                       MemberRepository memberRepository,
                       TaskInstanceRepository taskInstanceRepository,
                       UserRepository userRepository,
                       NotificationService notificationService) {
        this.homeRepository = homeRepository;
        this.taskRepository = taskRepository;
        this.memberRepository = memberRepository;
        this.taskInstanceRepository = taskInstanceRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    public Home createHome(CreateHomeRequest request) {
        // Crear el hogar
        Home home = new Home();
        home.setId(request.getId());
        home.setName(request.getName());
        home.setDescription(request.getDescription());
        home.setPrivate(request.isPrivate());

        home.setNotifyTaskReminders(true);
        home.setNotifyTaskCompleted(true);
        home.setNotifyNewMembers(true);
        home.setNotifyAllMembers(true);
        home.setForceSettings(false);

        // Generar código único
        String inviteCode = generateUniqueCode(request.getName());
        home.setInviteCode(inviteCode);

        List<Member> membersList = new ArrayList<>();

        // Creación de miembro Creador
        Member creator = new Member();
        creator.setId(request.getCreatorMemberId());
        creator.setUserId(request.getCreatorId());
        creator.setName(request.getCreatorName());
        creator.setLastName(request.getCreatorLastName());
        creator.setColorHex(request.getCreatorColorHex());
        creator.setRole("CREATOR");
        creator.setStatus("ACCEPTED");
        creator.setHome(home);

        membersList.add(creator);

        // Creación de miembros invitados
        if (request.getInvitedUsers() != null) {
            for (InvitedUserDto invite : request.getInvitedUsers()) {
                Member invited = new Member();
                invited.setId(invite.getId());
                invited.setName(invite.getTitle());
                invited.setUserId("");
                invited.setRole("MEMBER");
                invited.setStatus("PENDING");
                invited.setHome(home);
                membersList.add(invited);
            }
        }

        home.setMembers(membersList);

        return homeRepository.save(home);
    }

    @Transactional
    public Home updateHome(String homeId, UpdateHomeRequest request) throws Exception {
        Home home = homeRepository.findById(homeId)
                .orElseThrow(() -> new Exception("Hogar no encontrado"));

        if (request.getName() != null) {
            home.setName(request.getName());
        }
        if (request.getDescription() != null) {
            home.setDescription(request.getDescription());
        }
        if (request.getPrivate() != null) {
            home.setPrivate(request.getPrivate());
        }

        if (request.getName() != null){
            home.setName(request.getName());
        }

        if (request.getEditPermission() != null){
            home.setEditPermission(request.getEditPermission());
        }

        if (request.getNotifyTaskReminders() != null) {
            home.setNotifyTaskReminders(request.getNotifyTaskReminders());
        }
        if (request.getNotifyTaskCompleted() != null) {
            home.setNotifyTaskCompleted(request.getNotifyTaskCompleted());
        }
        if (request.getNotifyNewMembers() != null) {
            home.setNotifyNewMembers(request.getNotifyNewMembers());
        }
        if (request.getNotifyAllMembers() != null) {
            home.setNotifyAllMembers(request.getNotifyAllMembers());
        }
        if (request.getForceSettings() != null) {
            home.setForceSettings(request.getForceSettings());
        }

        return homeRepository.save(home);
    }


    private String generateUniqueCode(String homeName) {
        String prefix = homeName.length() >= 4
                ? homeName.substring(0, 4).toUpperCase()
                : (homeName + "HOME").substring(0, 4).toUpperCase();

        String code;
        do {
            code = prefix + "-" + generateRandomString(4);
        } while (homeRepository.existsByInviteCode(code));

        return code;
    }

    private String generateRandomString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }


    @Transactional
    public void deleteHome(String homeId) {
          homeRepository.deleteById(homeId);
    }

    @Transactional
    public Home regenerateInviteCode(String homeId) throws Exception {
        Home home = homeRepository.findById(homeId)
                .orElseThrow(() -> new Exception("Hogar no encontrado"));
        home.setInviteCode(generateUniqueCode(home.getName()));
        return homeRepository.save(home);
    }

    public HomePreviewDto findHomeByCode(String inviteCode, String userId) throws Exception {
        Home home = homeRepository.findByInviteCode(inviteCode)
                .orElseThrow(() -> new Exception("Hogar no encontrado"));

        String creatorName = home.getMembers().stream()
                .filter(m -> "CREATOR".equals(m.getRole()))
                .findFirst()
                .map(m -> userRepository.findById(m.getUserId())
                        .map(User::getName)
                        .orElse(m.getName())
                )
                .orElse("Desconocido");

        long taskCount = taskRepository.countByHomeId(home.getId());
        long pendingCount = taskInstanceRepository.countByHomeIdAndState(home.getId(), "PENDING");

        boolean isAlreadyMember = home.getMembers().stream()
                .anyMatch(m -> userId.equals(m.getUserId()));

        return new HomePreviewDto(
                home.getId(), home.getName(), creatorName,
                home.getMembers().size(), taskCount, pendingCount,
                isAlreadyMember
        );
    }

    @Transactional
    public Member joinHome(String inviteCode, String userId,
                           String name, String lastName, String colorHex) throws Exception {
        Home home = homeRepository.findByInviteCode(inviteCode)
                .orElseThrow(() -> new Exception("Hogar no encontrado"));

        boolean alreadyMember = home.getMembers().stream()
                .anyMatch(m -> userId.equals(m.getUserId()));
        if (alreadyMember) throw new Exception("Ya eres miembro de este hogar");

        Member member = new Member();
        member.setId(UUID.randomUUID().toString());
        member.setUserId(userId);
        member.setName(name);
        member.setLastName(lastName);
        member.setColorHex(colorHex);
        member.setRole("MEMBER");
        member.setStatus("ACCEPTED");
        member.setHome(home);

        member.setNotifyTaskReminders(home.isNotifyTaskReminders());
        member.setNotifyTaskCompleted(home.isNotifyTaskCompleted());
        member.setNotifyNewMembers(home.isNotifyNewMembers());

        home.getMembers().add(member);
        homeRepository.save(home);

        notificationService.notifyHomeMembers(
                home,
                "Nuevo miembro",
                name + " se unió al hogar",
                "NEW_MEMBER"
        );

        return member;
    }

    public List<HomeSyncDto> getHomesByUserId(String userId) {
        // Busca todos los hogares donde el usuario es miembro
        List<Member> memberships = memberRepository.findByUserId(userId);

        return memberships.stream()
                .map(m -> m.getHome())
                .distinct()
                .map(home -> {
                    HomeSyncDto dto = new HomeSyncDto();
                    dto.id = home.getId();
                    dto.name = home.getName();
                    dto.description = home.getDescription();
                    dto.isPrivate = home.isPrivate();
                    dto.inviteCode = home.getInviteCode();
                    dto.editPermission = home.getEditPermission();
                    dto.notifyTaskReminders = home.isNotifyTaskReminders();
                    dto.notifyTaskCompleted = home.isNotifyTaskCompleted();
                    dto.notifyNewMembers = home.isNotifyNewMembers();
                    dto.notifyAllMembers = home.isNotifyAllMembers();
                    dto.forceSettings = home.isForceSettings();
                    dto.members = home.getMembers().stream().map(mem -> {
                        MemberSyncDto mDto = new MemberSyncDto();
                        mDto.id = mem.getId();
                        mDto.userId = mem.getUserId();
                        mDto.name = mem.getName();
                        mDto.lastName = mem.getLastName();
                        mDto.colorHex = mem.getColorHex();
                        mDto.role = mem.getRole();
                        mDto.status = mem.getStatus();
                        return mDto;
                    }).collect(Collectors.toList());
                    return dto;
                })
                .collect(Collectors.toList());
    }




}