package com.cnusw.balancetalk.domain.vote.controller;

import com.cnusw.balancetalk.domain.vote.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/votes")
public class VoteRestController {
    private final VoteService voteService;

    @Autowired
    public VoteRestController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping("/{memberId}/{gameId}/first-option-voted")
    public ResponseEntity<?> voteToFirstOption(@PathVariable Long memberId, @PathVariable Long gameId) {
        voteService.voteToFirstOption(gameId, memberId);
        return ResponseEntity.ok("Voted to first option successfully");
    }

    @PostMapping("/{memberId}/{gameId}/second-option-voted")
    public ResponseEntity<?> voteToSecondOption(@PathVariable Long memberId, @PathVariable Long gameId) {
        voteService.voteToSecondOption(gameId, memberId);
        return ResponseEntity.ok("Voted to second option successfully");
    }
}
