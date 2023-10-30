package com.cnusw.balancetalk.domain.member.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import com.cnusw.balancetalk.domain.comment.repository.dto.response.CommentResponse;
import com.cnusw.balancetalk.domain.game.controller.response.GameResponse;
import com.cnusw.balancetalk.domain.game.entity.Game;
import com.cnusw.balancetalk.domain.game.repository.GameRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.cnusw.balancetalk.domain.member.entity.Member;
import com.cnusw.balancetalk.domain.member.repository.MemberRepository;
import com.cnusw.balancetalk.domain.member.dto.request.MemberLoginRequest;
import com.cnusw.balancetalk.domain.member.dto.response.MemberInfoResponse;
import com.cnusw.balancetalk.domain.member.dto.request.MemberJoinRequest;
import com.cnusw.balancetalk.global.jwt.JwtUtil;
import com.cnusw.balancetalk.global.utils.SecurityUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private static final String TOKEN_PREFIX = "Bearer ";

    private final MemberRepository memberRepository;
    private final GameRepository gameRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    /**
     * 1. 요청 객체의 이메일을 통해 이미 존재하는 회원인지 검증한다.
     * 2. 존재하지 않는 회원이면 요청 객체로부터 Member 엔티티를 생성하여 저장한다.
     * 3. 저장한 Member 엔티티의 비밀번호를 암호화한다.
     */
    public Long join(final MemberJoinRequest request) {

        if (memberRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new NoSuchElementException("이미 존재하는 이메일입니다.");
        }

        Member member = memberRepository.save(Member.from(request));

        // BCrypt 해싱 함수(BCrypt hashing function)를 사용해서 비밀번호를 암호화
        member.encodePassword(passwordEncoder);

        return member.getId();
    }

    /**
     * 1. 요청으로 온 이메일이 존재하는지 검증한다.
     * 2. 요청으로 온 비밀번호와 회원의 인코딩된 비밀번호를 비교하여 검증한다.
     * 3. 검증이 성공적으로 끝나면 토큰을 발급하여 반환한다.
     */
    public String login(final MemberLoginRequest request) {

        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 이메일입니다."));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())){
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        return jwtUtil.createToken(request.getEmail());
    }

    /**
     * 1. 요청으로 온 HTTP 헤더에서 토큰을 추출한다.
     * 2. 토큰에서 이메일을 추출한다.
     * 3. 추출한 이메일에 해당하는 회원의 정보를 반환한다.
     */
    @Transactional(readOnly = true)
    public MemberInfoResponse getMyInfo(final HttpServletRequest request) {
        String token = extractToken(request);
        String email = extractEmailFromToken(token);

        Member member = SecurityUtil.getCurrentEmail()
                .flatMap(currentEmail -> memberRepository.findByEmail(email))
                .orElseThrow(() -> new NoSuchElementException("Member not found"));

        return MemberInfoResponse.from(member);
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


    /**
     멤버의 글 리스트를 받아오는 메소드
     */
    public List<GameResponse> getMyGames(HttpServletRequest servletRequest){
        String bearerToken = servletRequest.getHeader("Authorization");
        String token = "";

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            token = bearerToken.substring(TOKEN_PREFIX.length());
        }

        String email = jwtUtil.extractSubject(token);
        Member member = memberRepository.findByEmail(email).orElseThrow();

        List<GameResponse> games = member.getGames().stream().map(GameResponse::from)
                .collect(Collectors.toList());

        return games;
    }

    /**
     멤버의 댓글 리스트를 받아오는 메소드
     */
    public List<CommentResponse> getMyComments(HttpServletRequest servletRequest){
        String bearerToken = servletRequest.getHeader("Authorization");
        String token = "";

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            token = bearerToken.substring(TOKEN_PREFIX.length());
        }

        String email = jwtUtil.extractSubject(token);
        Member member = memberRepository.findByEmail(email).orElseThrow();

        List<CommentResponse> comments = member.getComments().stream()
                .map(CommentResponse::of)
                .collect(Collectors.toList());

        return comments;
    }

    /*
    public List<GameResponse> getMyGamesByLike(HttpServletRequest servletRequest){
        String bearerToken = servletRequest.getHeader("Authorization");
        String token = "";

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            token = bearerToken.substring(TOKEN_PREFIX.length());
        }

        String email = jwtUtil.extractSubject(token);
        Member member = memberRepository.findByEmail(email).orElseThrow();


        return
    }
     */

}
