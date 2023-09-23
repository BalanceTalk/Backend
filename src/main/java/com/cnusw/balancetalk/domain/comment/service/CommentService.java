package com.cnusw.balancetalk.domain.comment.service;

import com.cnusw.balancetalk.domain.comment.dto.request.CommentRequest;
import com.cnusw.balancetalk.domain.comment.entity.Comment;
import com.cnusw.balancetalk.domain.comment.repository.CommentRepository;
import com.cnusw.balancetalk.domain.member.repository.MemberRepository;
import com.cnusw.balancetalk.domain.game.entity.Game;
import com.cnusw.balancetalk.domain.game.repository.GameRepository;
import com.cnusw.balancetalk.domain.member.entity.Member;
import com.cnusw.balancetalk.global.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final GameRepository gameRepository;
    private final MemberRepository memberRepository;

    private static final String TOKEN_PREFIX = "Bearer ";
    private final JwtUtil jwtUtil;

    public List<Comment> getCommentsService() {
        return commentRepository.findAll();
    }

    public Optional<Comment> makingComment(CommentRequest commentRequest, HttpServletRequest servletRequest, Long gameId) {

        String token = extractToken(servletRequest);
        String memberEmail = extractEmailFromToken(token);

        Game game = gameRepository.findGameById(gameId);
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow();

        Comment comment = commentRepository.save(commentRequest.toEntity(game, member));
        return Optional.of(comment);
    }

    // 요청 헤더에서 토큰 추출
    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }

        return null;
    }

    // 토큰에서 이메일 정보 추출
    private String extractEmailFromToken(String token) {
        return jwtUtil.extractSubject(token);
    }
}
