package com.cnusw.balancetalk.domain.comment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cnusw.balancetalk.domain.comment.entity.Comment;
import com.cnusw.balancetalk.domain.comment.entity.CommentDislikes;
import com.cnusw.balancetalk.domain.member.entity.Member;

public interface CommentDislikesRepository extends JpaRepository<CommentDislikes, Long> {

    @Query("select cdl from CommentDislikes cdl where cdl.member = :member and cdl.comment = :comment")
    Optional<CommentDislikes> findByMemberAndComment(Member member, Comment comment);
}
