package com.cnusw.balancetalk.domain.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cnusw.balancetalk.domain.comment.entity.Comment;
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    //List<Comment> findAllByTime(LocalDate time);
}