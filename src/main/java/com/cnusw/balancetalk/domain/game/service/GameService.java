package com.cnusw.balancetalk.domain.game.service;


import com.cnusw.balancetalk.domain.game.controller.request.GameRequest;
import com.cnusw.balancetalk.domain.game.controller.response.GameResponse;
import com.cnusw.balancetalk.domain.game.entity.Game;
import com.cnusw.balancetalk.domain.game.repository.GameRepository;
import com.cnusw.balancetalk.domain.game.service.Dto.GameDto;
import com.cnusw.balancetalk.domain.member.Member;
import com.cnusw.balancetalk.domain.option.controller.request.OptionRequest;
import com.cnusw.balancetalk.domain.option.entity.Option;
import com.cnusw.balancetalk.domain.option.repository.OptionRepository;
import com.cnusw.balancetalk.domain.option.service.Dto.OptionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=false)
public class GameService {
    private final GameRepository gameRepository;
    private final OptionRepository optionRepository;
//    public GameService(GameRepository gameRepository,OptionRepository optionRepository) {
//        this.gameRepository = gameRepository;
//        this.optionRepository = optionRepository;
//    }
    //게임 제작
    @Transactional
    public Long CreateGame(GameRequest gameRequest) {
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
                .build();

        return gameRepository.save(game).getId();
    }

    public Iterable<Game> getAllGames() {
        return gameRepository.findAll();
    }
    // 게임 목록 모두 조회
    public List<GameResponse> getGameListAll() {
        Iterable<Game> gameListAll = gameRepository.findAll();

        List<GameResponse> gameResponseListAll = new ArrayList<>();
        gameListAll.forEach((game) -> {
            gameResponseListAll.add(
                    GameResponse.builder()
                            .game_id(game.getId())
                            .user_id(game.getMember().getId())
                            .title(game.getTitle())
                            .playerCount(game.getPlayerCount())
                            .likes(game.getLikes())
                            .options(game.getOptions())
                            .build()
            );
        });

        return gameResponseListAll;
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
            gameResponses.add(convertToGameResponse(game));
        }
        return gameResponses;
    }

    private GameResponse convertToGameResponse(Game game) {
        Member member = game.getMember();
        return GameResponse.builder()
                .game_id(game.getId())
                .title(game.getTitle())
                .playerCount(game.getPlayerCount())
                .likes(game.getLikes())
                .user_id(member.getId())
                .build();
    }
    //게임 목록 모두 조회
    /*
    public List<GameResponse> getGameListAll(Long gameId){
        List<Game> gameListAll = gameRepository.findAllByGameId(gameId);

        List<GameResponse> gameResponseListAll = new ArrayList<>();
        gameListAll.forEach(
                (game->{
                    gameResponseListAll.add(
                            GameResponse.builder()
                                    .game_id(game.getId())
                                    .user_id(game.getMember().getId())
                                    .title(game.getTitle())
                                    .playerCount(game.getPlayerCount())
                                    .likes(game.getLikes())
                                    .options(game.getOptions())
                                    .build()
                    );
                 })
        );

        return gameResponseListAll;
        */
    //게임 화면 페이지
    public Game getGameById(Long gameId) {
        return gameRepository.findGameById(gameId);
    }

    //게임 목록 페이지
    public List<Game> getGameListAll() {
        return gameRepository.findAll();
    }

}

