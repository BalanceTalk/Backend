package com.cnusw.balancetalk.domain.comment.service;

import com.cnusw.balancetalk.domain.comment.dto.request.CommentRequest;
import com.cnusw.balancetalk.domain.comment.dto.response.CommentResponse;
import com.cnusw.balancetalk.domain.comment.entity.Comment;
import com.cnusw.balancetalk.domain.comment.entity.CommentDislikes;
import com.cnusw.balancetalk.domain.comment.entity.CommentLikes;
import com.cnusw.balancetalk.domain.comment.repository.CommentDislikesRepository;
import com.cnusw.balancetalk.domain.comment.repository.CommentLikesRepository;
import com.cnusw.balancetalk.domain.comment.repository.CommentRepository;
import com.cnusw.balancetalk.domain.member.repository.MemberRepository;
import com.cnusw.balancetalk.domain.game.entity.Game;
import com.cnusw.balancetalk.domain.game.repository.GameRepository;
import com.cnusw.balancetalk.domain.member.entity.Member;
import com.cnusw.balancetalk.global.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentLikesRepository commentLikesRepository;
    private final CommentDislikesRepository commentDislikesRepository;
    private final GameRepository gameRepository;
    private final MemberRepository memberRepository;

    private static final String TOKEN_PREFIX = "Bearer ";
    private final JwtUtil jwtUtil;

    public List<CommentResponse> getCommentsService(Long gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow();
        log.info("comments={}",game.getComments());

        return game.getComments().stream()
                .map(CommentResponse::of)
                .collect(Collectors.toList());
    }

    public Optional<Comment> makingComment(Long gameId, CommentRequest commentRequest, HttpServletRequest servletRequest) {

        String token = extractToken(servletRequest);
        String memberEmail = extractEmailFromToken(token);

        Game game = gameRepository.findGameById(gameId);
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow();

        Comment comment = commentRepository.save(commentRequest.toEntity(game, member));
        return Optional.of(comment);
    }

    public void likeComment(Long commentId, HttpServletRequest request) {
        String memberEmail = extractEmailFromToken(extractToken(request));
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow();

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow();

        Optional<CommentLikes> byMember = commentLikesRepository.findByMemberAndComment(member, comment);
        if (byMember.isPresent()) {
            return;
        }

        CommentLikes commentLikes = CommentLikes.builder()
                .member(member)
                .comment(comment)
                .build();
        commentLikesRepository.save(commentLikes);
    }

    public long getLikesCount(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow()
                .getLikes()
                .size();
    }

    public void dislikeComment(Long commentId, HttpServletRequest request) {
        String memberEmail = extractEmailFromToken(extractToken(request));
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow();

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow();

        Optional<CommentDislikes> byMember = commentDislikesRepository.findByMemberAndComment(member, comment);
        if (byMember.isPresent()) {
            return;
        }

        CommentDislikes commentDislikes = CommentDislikes.builder()
                .member(member)
                .comment(comment)
                .build();
        commentDislikesRepository.save(commentDislikes);
    }

    public long getDislikesCount(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow()
                .getDislikes()
                .size();
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
