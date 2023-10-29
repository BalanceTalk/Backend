package com.cnusw.balancetalk.domain.vote.controller;

import com.cnusw.balancetalk.domain.vote.entity.Vote;
import com.cnusw.balancetalk.domain.vote.service.VoteService;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Tag(name = "vote", description = "투표 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/games")
public class VoteRestController {

    private final VoteService voteService;

    @PostMapping("/{gameId}/vote")
    @Operation(summary = "선택지 투표", description = "선택지를 투표한 정보를 저장한다.")
    public Vote vote(
            @Parameter(name = "game id", description = "게임 아이디") @PathVariable Long gameId,
            @Parameter(name = "title", description = "투표할 선택지의 제목") @RequestParam("title") String selectedOptionTitle,
            HttpServletRequest servletRequest) {
        return voteService.vote(gameId, selectedOptionTitle, servletRequest);
    }
}

