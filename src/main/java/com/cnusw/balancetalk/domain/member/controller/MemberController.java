package com.cnusw.balancetalk.domain.member.controller;

import static org.springframework.http.HttpStatus.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cnusw.balancetalk.domain.member.dto.response.MemberInfoResponse;
import com.cnusw.balancetalk.domain.member.dto.request.MemberJoinRequest;
import com.cnusw.balancetalk.domain.member.dto.request.MemberLoginRequest;
import com.cnusw.balancetalk.domain.member.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "member", description = "회원 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    @ResponseStatus(CREATED)
    @Operation(summary = "회원가입", description = "회원가입을 통해 새로운 회원을 등록한다.")
    public Long join(@Valid @RequestBody MemberJoinRequest request) {
        return memberService.join(request);
    }

    @PostMapping("/login")
    @ResponseStatus(OK)
    @Operation(summary = "로그인", description = "로그인을 통해 해당 회원의 토큰을 가져온다.")
    public String login(@Valid @RequestBody MemberLoginRequest request) {
        return memberService.login(request);
    }

    @GetMapping("/info")
    @ResponseStatus(OK)
    @Operation(summary = "회원 정보 조회", description = "로그인 되어 있는 회원 정보를 가져온다.")
    public MemberInfoResponse myInfo(HttpServletRequest request) {
        return memberService.getMyInfo(request);
    }
}