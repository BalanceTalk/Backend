package com.cnusw.balancetalk.domain.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cnusw.balancetalk.domain.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}