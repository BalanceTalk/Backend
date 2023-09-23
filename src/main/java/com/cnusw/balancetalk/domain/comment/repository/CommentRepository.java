package com.cnusw.balancetalk.domain.comment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.cnusw.balancetalk.domain.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}