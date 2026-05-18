package com.haptikos.gestor_tareas_haptikos_servidor.service;

import com.haptikos.gestor_tareas_haptikos_servidor.dto.CreateMemberRequest;
import com.haptikos.gestor_tareas_haptikos_servidor.dto.MemberDto;
import com.haptikos.gestor_tareas_haptikos_servidor.model.Home;
import com.haptikos.gestor_tareas_haptikos_servidor.model.Member;
import com.haptikos.gestor_tareas_haptikos_servidor.repository.HomeRepository;
import com.haptikos.gestor_tareas_haptikos_servidor.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final HomeRepository homeRepository;
    private final NotificationService notificationService;

    public MemberService(MemberRepository memberRepository, HomeRepository homeRepository, NotificationService notificationService) {
        this.memberRepository = memberRepository;
        this.homeRepository = homeRepository;
        this.notificationService = notificationService;
    }

    public void createMember(CreateMemberRequest request) {
        Member member = new Member();

        member.setId(request.getId());
        member.setUserId(request.getUserId());
        member.setName(request.getName());
        member.setLastName(request.getLastName());
        member.setColorHex(request.getColorHex());
        member.setRole(request.getRole());
        member.setStatus(request.getStatus());

        Home home = homeRepository.findById(request.getHomeId())
                .orElseThrow(() -> new RuntimeException("Hogar no encontrado con id: " + request.getHomeId()));

        member.setHome(home);
        memberRepository.save(member);

        String actorId = request.getUserId() != null ? request.getUserId() : "";
        notificationService.sendSilentSyncToHome(home, "SYNC_MEMBERS", actorId);

    }

    public List<MemberDto> getMembersByHomeId(String homeId) {
        List<Member> members = memberRepository.findByHomeId(homeId);

        return members.stream().map(member -> {
            MemberDto dto = new MemberDto();
            dto.setId(member.getId());
            dto.setUserId(member.getUserId());
            dto.setName(member.getName());
            dto.setLastName(member.getLastName());
            dto.setColorHex(member.getColorHex());
            dto.setRole(member.getRole());
            dto.setStatus(member.getStatus());

            if (member.getHome() != null) {
                dto.setHomeId(member.getHome().getId());
            }

            return dto;
        }).collect(Collectors.toList());
    }
}