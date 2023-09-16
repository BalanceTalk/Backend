package com.cnusw.balancetalk.domain.comment.repository;

import com.cnusw.balancetalk.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findMemberById(Long id);
}
