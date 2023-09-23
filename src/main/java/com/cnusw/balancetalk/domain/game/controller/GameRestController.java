package com.cnusw.balancetalk.domain.game.controller;


import com.cnusw.balancetalk.domain.game.controller.request.GameRequest;
import com.cnusw.balancetalk.domain.game.controller.response.GameResponse;
import com.cnusw.balancetalk.domain.game.entity.Game;
import com.cnusw.balancetalk.domain.game.service.GameService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class GameRestController {

    private final GameService gameService;

    @PostMapping("/games/create")
    public ResponseEntity<Long> create(@RequestBody GameRequest request, HttpServletRequest servletRequest) {
        return ResponseEntity.ok(gameService.createGame(request, servletRequest));
    }

    @GetMapping("/games")
    public List<GameResponse> getGames(@RequestParam(name = "sortBy", required = false) String sortBy) {
        return getGameResponses(sortBy);
    }

    private List<GameResponse> getGameResponses(String sortBy) {
        if ("popularity".equals(sortBy)) {
            return gameService.getGamesSortedByPopularity();
        } else if ("views".equals(sortBy)) {
            return gameService.getGamesSortedByViews();
        } else if ("latest".equals(sortBy)) {
            return gameService.getGamesSortedByLatest();
        } else {
            // 기본적으로는 인기순으로 정렬된 결과를 반환
            return gameService.getGamesSortedByPopularity();
        }
    }

    /**
     * 선택지 투표 페이지랑 / 게임 페이지 중복 되는것같은데..??
     */
    @GetMapping("/games/{id}")
    public GameResponse findById(@PathVariable Long id) {
        return gameService.findById(id);
    }
}
