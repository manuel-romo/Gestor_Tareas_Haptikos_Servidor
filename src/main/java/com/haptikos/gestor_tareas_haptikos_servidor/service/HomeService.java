package com.haptikos.gestor_tareas_haptikos_servidor.service;

import com.haptikos.gestor_tareas_haptikos_servidor.dto.CreateHomeRequest;
import com.haptikos.gestor_tareas_haptikos_servidor.dto.InvitedUserDto;
import com.haptikos.gestor_tareas_haptikos_servidor.dto.UpdateHomeRequest;
import com.haptikos.gestor_tareas_haptikos_servidor.model.Home;
import com.haptikos.gestor_tareas_haptikos_servidor.model.Member;
import com.haptikos.gestor_tareas_haptikos_servidor.repository.HomeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class HomeService {

    private final HomeRepository homeRepository;

    public HomeService(HomeRepository homeRepository) {
        this.homeRepository = homeRepository;
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
        creator.setId(request.getCreatorId());
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

    /**
     *
     * @Transactional
     *     public void deleteHome(String homeId) {
     *         homeRepository.deleteById(homeId);
     *     }
     */

}