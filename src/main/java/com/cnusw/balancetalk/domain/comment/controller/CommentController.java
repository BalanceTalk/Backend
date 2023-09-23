package com.cnusw.balancetalk.domain.comment.controller;

import com.cnusw.balancetalk.domain.comment.service.CommentService;
import com.cnusw.balancetalk.domain.comment.dto.request.CommentRequest;
import com.cnusw.balancetalk.domain.comment.entity.Comment;
import com.cnusw.balancetalk.domain.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final MemberService memberService;

    @GetMapping("/games/{id}")
    public List<Comment> getComments() {
        return commentService.getCommentsService();
    }

    @PostMapping("/games/{id}/comment")
    public /*ResponseEntity<Comment>*/ String makeComment(@RequestBody CommentRequest commentRequest, HttpServletRequest servletRequest, @PathVariable("id") Long gameId) {
        Optional<Comment> comment = commentService.makingComment(commentRequest, servletRequest, gameId);
        //return comment.map(value -> new ResponseEntity<>(value, new HttpHeaders(), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, null, HttpStatus.FOUND));
        return "등록 완료";
    }

}
