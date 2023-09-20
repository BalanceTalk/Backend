package com.cnusw.balancetalk.domain.comment.application;

import com.cnusw.balancetalk.domain.comment.dto.request.CommentRequest;
import com.cnusw.balancetalk.domain.comment.entity.Comment;
import com.cnusw.balancetalk.domain.comment.repository.CommentRepository;
import com.cnusw.balancetalk.domain.comment.repository.MemberRepository;
import com.cnusw.balancetalk.domain.game.entity.Game;
import com.cnusw.balancetalk.domain.game.repository.GameRepository;
import com.cnusw.balancetalk.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final GameRepository gameRepository;
    private final MemberRepository memberRepository;


    public List<Comment> getCommentsService() {
        return commentRepository.findAll();
    }

    public Optional<Comment> makingComment(CommentRequest commentRequest, Long gameId, Long memberId) {
        Game game = gameRepository.findGameById(gameId);
        Member member = memberRepository.findMemberById(memberId);

        Comment comment = commentRepository.save(commentRequest.toEntity(game, member));
        return Optional.of(comment);
    }
}
