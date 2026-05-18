package com.haptikos.gestor_tareas_haptikos_servidor.repository;

import com.haptikos.gestor_tareas_haptikos_servidor.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {

    List<Member> findByUserId(String userId);
    List<Member> findByHomeId(String homeId);
    Optional<Member> findByHomeIdAndUserId(String homeId, String userId);

}