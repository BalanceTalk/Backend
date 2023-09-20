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

    //게임 제작
    @Transactional
    public Long CreateGame(GameRequest gameRequest) {
        Game game = Game.builder()
                .title(gameRequest.getTitle())
                //.deadline(gameRequest.getDeadline())
                //.options(option)
                .build();
        //일대다 매핑이 된 부분은 list타입으로 되어있는데, 서비스단에 어떻게 구현해야할 지 모르겠음
        return gameRepository.save(game).getId();
    }

    //게임 화면 페이지
    public Game getGameById(Long gameId) {
        return gameRepository.findGameById(gameId);
    }

    //게임 목록 페이지
    public List<Game> getGameListAll() {
        List<Game> gameListAll = gameRepository.findAll();
        return gameListAll;
    }

}

