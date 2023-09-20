package com.cnusw.balancetalk.domain.comment.api;

import com.cnusw.balancetalk.domain.comment.application.CommentService;
import com.cnusw.balancetalk.domain.comment.dto.request.CommentRequest;
import com.cnusw.balancetalk.domain.comment.entity.Comment;
import lombok.Getter;
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

    @GetMapping("games/{id}")
    public List<Comment> getComments() {
        return commentService.getCommentsService();
    }

    @PostMapping("/games/{id}/comment")
    public /*ResponseEntity<Comment>*/ String makeComment(@RequestBody CommentRequest commentRequest, @PathVariable("id") Long gameId) {
        Long playerId = 1L;
        Optional<Comment> comment = commentService.makingComment(commentRequest, gameId, playerId);
        //return comment.map(value -> new ResponseEntity<>(value, new HttpHeaders(), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, null, HttpStatus.FOUND));
        return "등록 완료";
    }

}
