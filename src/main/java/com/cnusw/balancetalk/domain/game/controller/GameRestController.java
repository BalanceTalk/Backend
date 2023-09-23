package com.cnusw.balancetalk.domain.game.controller;


import com.cnusw.balancetalk.domain.game.controller.request.GameRequest;
import com.cnusw.balancetalk.domain.game.entity.Game;
import com.cnusw.balancetalk.domain.game.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class GameRestcontroller {
    private final GameService gameService;

    @PostMapping("/games/create")
    public ResponseEntity<Long> Create(@RequestBody GameRequest request){
        return ResponseEntity.ok(gameService.CreateGame(request));
    }
    
    /**
     *
     * 선택지 투표 페이지랑 / 게임 페이지 중복 되는것같은데..??
     */


    //Game 전체 목록 출력
    @GetMapping
    public List<Game> getAllGameList() {
        return gameService.getGameListAll();
    }


}
