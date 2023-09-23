package com.cnusw.balancetalk.domain.game.service;



import com.cnusw.balancetalk.domain.game.controller.request.GameRequest;
import com.cnusw.balancetalk.domain.game.controller.response.GameResponse;
import com.cnusw.balancetalk.domain.game.entity.Game;
import com.cnusw.balancetalk.domain.game.repository.GameRepository;
import com.cnusw.balancetalk.domain.member.entity.Member;
import com.cnusw.balancetalk.domain.member.repository.MemberRepository;
import com.cnusw.balancetalk.domain.member.service.MemberService;
import com.cnusw.balancetalk.domain.option.entity.Option;
import com.cnusw.balancetalk.domain.option.repository.OptionRepository;
import com.cnusw.balancetalk.global.jwt.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GameService {

    private static final String TOKEN_PREFIX = "Bearer ";

    private final GameRepository gameRepository;
    private final OptionRepository optionRepository;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    //게임 제작
    public Long createGame(GameRequest gameRequest, HttpServletRequest servletRequest) {
        String bearerToken = servletRequest.getHeader("Authorization");

        String token = "";
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            token = bearerToken.substring(TOKEN_PREFIX.length());
        }

        String email = jwtUtil.extractSubject(token);
        Member member = memberRepository.findByEmail(email).orElseThrow();

        Option option1 = Option.builder()
                .title(gameRequest.getOptionTitle1())
                .description(gameRequest.getOptionDescription1())
                .imgUrl(gameRequest.getOptionImgUrl1())
                .build();

        Option option2 = Option.builder()
                .title(gameRequest.getOptionTitle2())
                .description(gameRequest.getOptionDescription2())
                .imgUrl(gameRequest.getOptionImgUrl2())
                .build();

        List<Option> options = new ArrayList<>();
        options.add(option1);
        options.add(option2);

        Game game = Game.builder()
                .title(gameRequest.getTitle())
                .deadline(gameRequest.getDeadline())
                .options(options)
                .member(member)
                .build();

        gameRepository.save(game);

        options.forEach(option -> option.addGame(game));

        optionRepository.save(option1);
        optionRepository.save(option2);

        return game.getId();
    }

    public GameResponse findById(Long id) {
        Game game = gameRepository.findById(id).orElseThrow();
        return GameResponse.from(game);
    }

    public List<GameResponse> getGamesSortedByPopularity() {
        // 인기순
        Sort sort = Sort.by(Sort.Order.desc("likes"));
        List<Game> games = gameRepository.findAll(sort);
        return convertToGameResponseList(games);
    }

    public List<GameResponse> getGamesSortedByViews() {
        // 조회수순
        Sort sort = Sort.by(Sort.Order.desc("playerCount"));
        List<Game> games = gameRepository.findAll(sort);
        return convertToGameResponseList(games);
    }

    public List<GameResponse> getGamesSortedByLatest() {
        // 최신순
        Sort sort = Sort.by(Sort.Order.desc("createdAt"));
        List<Game> games = gameRepository.findAll(sort);
        return convertToGameResponseList(games);
    }

    private List<GameResponse> convertToGameResponseList(List<Game> games) {
        // Game 엔티티를 GameResponse로 변환하여 리스트로 반환
        List<GameResponse> gameResponses = new ArrayList<>();
        for (Game game : games) {
            gameResponses.add(GameResponse.from(game));
        }
        return gameResponses;
    }
}

