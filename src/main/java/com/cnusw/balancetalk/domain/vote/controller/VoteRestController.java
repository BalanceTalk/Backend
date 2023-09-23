package com.cnusw.balancetalk.domain.vote.controller;

import com.cnusw.balancetalk.domain.vote.entity.Vote;
import com.cnusw.balancetalk.domain.vote.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/games")
public class VoteRestController {

    private final VoteService voteService;

    @PostMapping("/{gameId}/vote")
    public Vote vote(@PathVariable Long gameId, @RequestParam("title") String selectedOptionTitle, HttpServletRequest servletRequest) {
        return voteService.vote(gameId, selectedOptionTitle, servletRequest);
    }
}
