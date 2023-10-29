package com.cnusw.balancetalk.domain.game.service;

import com.cnusw.balancetalk.domain.game.controller.request.GameRequest;
import com.cnusw.balancetalk.domain.game.controller.response.CategoryGamesResponse;
import com.cnusw.balancetalk.domain.game.controller.response.GameResponse;
import com.cnusw.balancetalk.domain.game.entity.Game;
import com.cnusw.balancetalk.domain.game.enums.Category;
import com.cnusw.balancetalk.domain.game.repository.GameRepository;
import com.cnusw.balancetalk.domain.member.entity.Member;
import com.cnusw.balancetalk.domain.member.repository.MemberRepository;
import com.cnusw.balancetalk.domain.option.entity.Option;
import com.cnusw.balancetalk.domain.option.repository.OptionRepository;
import com.cnusw.balancetalk.global.jwt.JwtUtil;
import com.cnusw.balancetalk.domain.vote.repository.VoteRepository;

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
    private final VoteRepository voteRepository;
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

    // 인기순으로 게임 정렬 리스트 반환
    public List<GameResponse> getGamesSortedByPopularity() {
        Sort sort = Sort.by(Sort.Order.desc("likes"));
        List<Game> games = gameRepository.findAllByActivationTrue(sort);
        return convertToGameResponseList(games);
    }

    // 조회수순으로 게임 정렬 리스트 반환
    public List<GameResponse> getGamesSortedByViews() {
        Sort sort = Sort.by(Sort.Order.desc("playerCount"));
        List<Game> games = gameRepository.findAllByActivationTrue(sort);
        return convertToGameResponseList(games);
    }

    // 최신순으로 게임 정렬 리스트 반환
    public List<GameResponse> getGamesSortedByLatest() {
        Sort sort = Sort.by(Sort.Order.desc("createdAt"));
        List<Game> games = gameRepository.findAllByActivationTrue(sort);
        return convertToGameResponseList(games);
    }

    // getmapping('/')
    // 메인페이지.
    // 여러 카테고리의 게임리스트를 하나의 리스트로 전달.
    public List<CategoryGamesResponse> getCategoryGamesList() {
        List<CategoryGamesResponse> categoryGamesResponses = new ArrayList<>();
        categoryGamesResponses.add(getCategoryGamesOfGoldenBalance());
        categoryGamesResponses.add(getCategoryGamesOfPopularity());
        categoryGamesResponses.add(getCategoryGamesOfViews());
        categoryGamesResponses.add(getCategoryGamesOfLatest());
        return categoryGamesResponses;
    }

    // 황금밸런스 카테고리
    // GameResponse 리스트와 카테고리를 CategoryGamesResponse Dto로 변환 후 반환
    private CategoryGamesResponse getCategoryGamesOfGoldenBalance() {
        List<Game> games = gameRepository.findAll();
        List<GameResponse> allGameResponses = convertToGameResponseList(games);
        List<GameResponse> goldenBalanceGameResponses = new ArrayList<>();

        for (GameResponse gameResponse : allGameResponses) {
            Option option1 = optionRepository.findOptionById(gameResponse.getOptionId1());
            Option option2 = optionRepository.findOptionById(gameResponse.getOptionId2());
            int firstOptionVoteCount = voteRepository.findVotesByOption(option1.getId()).size();
            int secondOptionVoteCount = voteRepository.findVotesByOption(option2.getId()).size();
            double percentage = (firstOptionVoteCount / (firstOptionVoteCount+secondOptionVoteCount)) * 100.0;
            if ((percentage <= 55) && (percentage >= 45) ) { goldenBalanceGameResponses.add(gameResponse); }
        }

        return CategoryGamesResponse.from(goldenBalanceGameResponses, Category.GOLDENBALANCE);
    }

    // 인기순 카테고리
    // GameResponse 리스트와 카테고리를 CategoryGamesResponse Dto로 변환 후 반환
    private CategoryGamesResponse getCategoryGamesOfPopularity() {
        List<GameResponse> gameResponses = getGamesSortedByPopularity();
        return CategoryGamesResponse.from(gameResponses, Category.POPULARITY);
    }

    // 조회수순 카테고리
    // GameResponse 리스트와 카테고리를 CategoryGamesResponse Dto로 변환 후 반환
    private CategoryGamesResponse getCategoryGamesOfViews() {
        List<GameResponse> gameResponses = getGamesSortedByViews();
        return CategoryGamesResponse.from(gameResponses, Category.VIEWS);
    }

    // 최신순 카테고리
    // GameResponse 리스트와 카테고리를 CategoryGamesResponse Dto로 변환 후 반환
    private CategoryGamesResponse getCategoryGamesOfLatest() {
        List<GameResponse> gameResponses = getGamesSortedByViews();
        return CategoryGamesResponse.from(gameResponses, Category.LATEST);
    }

    // Game 엔티티를 GameResponse로 변환하여 리스트로 반환
    private List<GameResponse> convertToGameResponseList(List<Game> games) {
        List<GameResponse> gameResponses = new ArrayList<>();
        for (Game game : games) {
            gameResponses.add(GameResponse.from(game));
        }
        return gameResponses;
    }

    public void reportGame(Long id) {
        Game game = gameRepository.findById(id).orElseThrow();
        long currentReports = game.getReports();
        game.setReports(currentReports + 1);
        if (currentReports + 1 >= 5) {
            game.setActivation(false);
        }
    }
}

