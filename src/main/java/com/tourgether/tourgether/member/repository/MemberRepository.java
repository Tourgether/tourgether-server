package com.tourgether.tourgether.member.repository;

import com.tourgether.tourgether.member.entity.Member;
import com.tourgether.tourgether.member.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByIdAndStatus(Long id, Status status);

    default Member getActiveMemberOrThrow(Long id) {
        return findByIdAndStatus(id, Status.ACTIVE)
            .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));
    }
}
