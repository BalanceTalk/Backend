package com.cnusw.balancetalk.domain.comment.service;

import com.cnusw.balancetalk.domain.comment.repository.dto.request.CommentRequest;
import com.cnusw.balancetalk.domain.comment.repository.dto.response.CommentResponse;
import com.cnusw.balancetalk.domain.comment.entity.Comment;
import com.cnusw.balancetalk.domain.comment.entity.CommentDislikes;
import com.cnusw.balancetalk.domain.comment.entity.CommentLikes;
import com.cnusw.balancetalk.domain.comment.entity.CommentReports;
import com.cnusw.balancetalk.domain.comment.repository.CommentDislikesRepository;
import com.cnusw.balancetalk.domain.comment.repository.CommentLikesRepository;
import com.cnusw.balancetalk.domain.comment.repository.CommentReportsRepository;
import com.cnusw.balancetalk.domain.comment.repository.CommentRepository;
import com.cnusw.balancetalk.domain.member.repository.MemberRepository;
import com.cnusw.balancetalk.domain.game.entity.Game;
import com.cnusw.balancetalk.domain.game.repository.GameRepository;
import com.cnusw.balancetalk.domain.member.entity.Member;
import com.cnusw.balancetalk.domain.member.service.MemberService;
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
    private final CommentReportsRepository commentReportsRepository;
    private final CommentDislikesRepository commentDislikesRepository;
    private final GameRepository gameRepository;
    private final MemberRepository memberRepository;

    private final MemberService memberService;

    private static final String TOKEN_PREFIX = "Bearer ";
    private final JwtUtil jwtUtil;

    public List<CommentResponse> getCommentsService(Long gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow();
        log.info("comments={}",game.getComments());

        return game.getComments().stream()
                .map(comment -> {
                    if (comment.isActivation()) {
                        return CommentResponse.of(comment);
                    } else return CommentResponse.deactivatedOf(comment);
                })
                .collect(Collectors.toList());
    }

    public Optional<Comment> makingComment(Long gameId, CommentRequest commentRequest, HttpServletRequest servletRequest) {

        String token = extractToken(servletRequest);
        String memberEmail = extractEmailFromToken(token);

        Game game = gameRepository.findGameById(gameId);
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow();

        Comment comment = commentRepository.save(commentRequest.toEntity(game, member));

        // Increase 5 EXP for commentMaker
        boolean levelUpdated = memberService.updateMemberLevel(member.getId(), 5);
        if(levelUpdated) {
            // If level increased, player can earn 100 credit
            member.setCredit(member.getCredit() + 100);
            log.info("Member {} leveled up to level {}", member.getId(), member.getLevel());
            log.info("Member {} earned credit {}", member.getId(), 100);
        }

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

    public void reportComment(Long commentId, HttpServletRequest request) {
        String memberEmail = extractEmailFromToken(extractToken(request));
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow();

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow();

        Optional<CommentLikes> byMember = commentReportsRepository.findByMemberAndComment(member, comment);
        if (byMember.isPresent()) {
            return;
        }

        CommentReports commentReports = CommentReports.builder()
                .member(member)
                .comment(comment)
                .build();
        commentReportsRepository.save(commentReports);

        long currentReports = comment.getReports();
        comment.setReports(currentReports + 1);
        if (currentReports + 1 >= 5) {
            comment.setActivation(false);
        }

    }
}
