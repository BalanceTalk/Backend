package com.cnusw.balancetalk.domain.vote.service;

import java.util.List;

import com.cnusw.balancetalk.domain.game.entity.Game;
import com.cnusw.balancetalk.domain.game.repository.GameRepository;
import com.cnusw.balancetalk.domain.game.service.GameService;
import com.cnusw.balancetalk.domain.member.entity.Member;
import com.cnusw.balancetalk.domain.member.repository.MemberRepository;
import com.cnusw.balancetalk.domain.option.entity.Option;
import com.cnusw.balancetalk.domain.option.repository.OptionRepository;
import com.cnusw.balancetalk.domain.vote.repository.VoteRepository;
import com.cnusw.balancetalk.domain.vote.entity.Vote;
import com.cnusw.balancetalk.global.jwt.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class VoteService {

    private static final String TOKEN_PREFIX = "Bearer ";

    private final GameRepository gameRepository;
    private final VoteRepository voteRepository;
    private final MemberRepository memberRepository;
    private final GameService gameService;
    private final JwtUtil jwtUtil;

    public Vote vote(Long gameId, String selectedOptionTitle, HttpServletRequest servletRequest) {
        log.info("selected title = {}", selectedOptionTitle);
        Member member = getMember(servletRequest);

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new ResourceNotFoundException("Game not found with id " + gameId));

        log.info("game = {}", game.getId());
        List<Option> options = game.getOptions();

        log.info("option title1 = {}", options.get(0).getTitle());
        log.info("option title2 = {}", options.get(1).getTitle());
        Option selectedOption = null;
        for (Option option : options) {
            log.info("titletiteltletietlei={}", option.getTitle());
            log.info("selectedTitle type={}", selectedOptionTitle.getClass());
            log.info("title type={}", option.getTitle().getClass());
            log.info("title tostr={}", selectedOptionTitle.toString());
            log.info("title tostr={}", option.getTitle().toString());
            if (option.getTitle().equals(selectedOptionTitle)) {
                selectedOption = option;
            }
        }

        log.info("title = {}", selectedOption.getTitle());

        Vote vote = Vote.builder()
                .member(member)
                .option(selectedOption)
                .build();
        //game.setSecondOptionVotedCount(game.getSecondOptionVotedCount() + 1);

        gameService.updateGoldBalance(game);

        return voteRepository.save(vote);
    }

    private Member getMember(HttpServletRequest servletRequest) {
        String bearerToken = servletRequest.getHeader("Authorization");

        String token = "";
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            token = bearerToken.substring(TOKEN_PREFIX.length());
        }

        String email = jwtUtil.extractSubject(token);
        return memberRepository.findByEmail(email).orElseThrow();
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

