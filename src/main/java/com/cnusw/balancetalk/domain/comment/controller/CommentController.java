package com.cnusw.balancetalk.domain.comment.controller;

import com.cnusw.balancetalk.domain.comment.dto.response.CommentResponse;
import com.cnusw.balancetalk.domain.comment.service.CommentService;
import com.cnusw.balancetalk.domain.comment.dto.request.CommentRequest;
import com.cnusw.balancetalk.domain.comment.entity.Comment;

import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/games/{id}/result")
    public List<CommentResponse> getComments(@PathVariable Long id) {
        return commentService.getCommentsService(id);
    }

    @PostMapping("/games/{id}/comment")
    public CommentResponse makeComment(@PathVariable("id") Long gameId, @RequestBody CommentRequest commentRequest, HttpServletRequest servletRequest) {
        Comment comment = commentService.makingComment(gameId, commentRequest, servletRequest).orElseThrow();
        return CommentResponse.of(comment);
    }
}
