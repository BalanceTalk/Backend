package com.cnusw.balancetalk.domain.game.controller;


import com.cnusw.balancetalk.domain.game.controller.request.GameRequest;
import com.cnusw.balancetalk.domain.game.controller.response.GameResponse;
import com.cnusw.balancetalk.domain.game.entity.Game;
import com.cnusw.balancetalk.domain.game.service.Dto.GameDto;
import com.cnusw.balancetalk.domain.game.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class GameRestcontroller {
    private final GameService gameService;

    @PostMapping("/games/create")
    public ResponseEntity<Long> Create(@RequestBody GameRequest request){
        return ResponseEntity.ok(gameService.CreateGame(request));
    }



}
