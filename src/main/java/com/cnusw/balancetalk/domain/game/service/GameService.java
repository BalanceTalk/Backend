package com.cnusw.balancetalk.domain.game.service;


import com.cnusw.balancetalk.domain.game.controller.request.GameRequest;
import com.cnusw.balancetalk.domain.game.controller.response.GameResponse;
import com.cnusw.balancetalk.domain.game.entity.Game;
import com.cnusw.balancetalk.domain.game.repository.GameRepository;
import com.cnusw.balancetalk.domain.game.service.Dto.GameDto;
import com.cnusw.balancetalk.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=false)
public class GameService {
    private final GameRepository gameRepository;

    //게임 제작
    @Transactional
    public Long CreateGame(GameRequest gameRequest){
        Game game = Game.builder()
                .title(gameRequest.getTitle())
                .firstOptionVotedCount(0)
                .secondOptionVotedCount(0)
                //.deadline(gameRequest.getDeadline())
                //.options(gameRequest.getOptions())
                .build();
        //일대다 매핑이 된 부분은 list타입으로 되어있는데, 서비스단에 어떻게 구현해야할 지 모르겠음
        return gameRepository.save(game).getId();
    }

    public Optional<Game> getGameById(Long gameId) {
        return gameRepository.findById(gameId);
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


    }

