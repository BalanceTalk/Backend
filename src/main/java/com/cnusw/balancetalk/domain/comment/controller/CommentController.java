package com.cnusw.balancetalk.domain.comment.controller;

import com.cnusw.balancetalk.domain.comment.dto.response.CommentResponse;
import com.cnusw.balancetalk.domain.comment.service.CommentService;
import com.cnusw.balancetalk.domain.comment.dto.request.CommentRequest;
import com.cnusw.balancetalk.domain.comment.entity.Comment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "comment", description = "댓글 API")
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/games/{id}/result")
    @Operation(summary = "모든 댓글 조회", description = "게임에 존재하는 모든 댓글을 가져온다.")
    public List<CommentResponse> getComments(@Parameter(name = "game id", description = "게임 아이디") @PathVariable Long gameId) {
        return commentService.getCommentsService(gameId);
    }

    @PostMapping("/games/{id}/comment")
    @Operation(summary = "댓글 작성", description = "댓글을 작성한다.")
    public CommentResponse makeComment(
            @Parameter(name = "game id", description = "게임 아이디") @PathVariable("id") Long gameId,
            @RequestBody CommentRequest commentRequest,
            HttpServletRequest servletRequest) {
        Comment comment = commentService.makingComment(gameId, commentRequest, servletRequest).orElseThrow();
        return CommentResponse.of(comment);
    }
}
