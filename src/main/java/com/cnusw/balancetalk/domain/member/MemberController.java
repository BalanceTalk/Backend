package com.cnusw.balancetalk.domain.member;

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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    @ResponseStatus(CREATED)
    public Long join(@Valid @RequestBody MemberJoinRequest request) {
        return memberService.join(request);
    }

    @PostMapping("/login")
    @ResponseStatus(OK)
    public String login(@Valid @RequestBody MemberLoginRequest request) {
        return memberService.login(request);
    }

    @GetMapping("/info")
    @ResponseStatus(OK)
    public MemberInfoResponse myInfo(HttpServletRequest request) {
        return memberService.getMyInfo(request);
    }
}