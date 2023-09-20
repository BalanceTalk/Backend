package com.cnusw.balancetalk.domain.game.controller;


import com.cnusw.balancetalk.domain.game.controller.request.GameRequest;
import com.cnusw.balancetalk.domain.game.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/games")
public class GameRestController {
    private final GameService gameService;

    @PostMapping("/create")
    public ResponseEntity<Long> Create(@RequestBody GameRequest request){
        return ResponseEntity.ok(gameService.CreateGame(request));
    }
}
