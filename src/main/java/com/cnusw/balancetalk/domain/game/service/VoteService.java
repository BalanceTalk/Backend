package com.cnusw.balancetalk.domain.game.service;

import com.cnusw.balancetalk.domain.game.entity.Game;
import com.cnusw.balancetalk.domain.game.repository.GameRepository;
import com.cnusw.balancetalk.domain.game.repository.VoteRepository;
import com.cnusw.balancetalk.domain.vote.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteService {
    @Autowired
    private GameRepository gameRepository;
    private VoteRepository voteRepository;

    public void voteToFirstOption(Long gameId, Long memberId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new ResourceNotFoundException("Game not found with id " + gameId));
        Vote vote = Vote.builder()
                .voterId(memberId)
                .isVotedFirstOption(true)
                .isVotedSecondOption(false)
            .build();
        //game.setFirstOptionVotedCount(game.getFirstOptionVotedCount() + 1);
        gameRepository.save(game);
        voteRepository.save(vote);
    }

    public void voteToSecondOption(Long gameId, Long memberId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new ResourceNotFoundException("Game not found with id " + gameId));
        Vote vote = Vote.builder()
                .voterId(memberId)
                .isVotedFirstOption(false)
                .isVotedSecondOption(true)
                .build();
        //game.setSecondOptionVotedCount(game.getSecondOptionVotedCount() + 1);
        gameRepository.save(game);
        voteRepository.save(vote);
    }

    public static class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException() {
            super();
        }
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }

}

