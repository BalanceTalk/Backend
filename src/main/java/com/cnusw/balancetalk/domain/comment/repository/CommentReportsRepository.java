package com.cnusw.balancetalk.domain.comment.repository;

import com.cnusw.balancetalk.domain.comment.entity.Comment;
import com.cnusw.balancetalk.domain.comment.entity.CommentLikes;
import com.cnusw.balancetalk.domain.comment.entity.CommentReports;
import com.cnusw.balancetalk.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CommentReportsRepository extends JpaRepository<CommentReports, Long> {

    @Query("select cl from CommentLikes cl where cl.member = :member and cl.comment = :comment")
    Optional<CommentLikes> findByMemberAndComment(Member member, Comment comment);
}
