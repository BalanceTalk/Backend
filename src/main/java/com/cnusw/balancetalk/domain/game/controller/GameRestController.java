package com.cnusw.balancetalk.domain.game.controller;


import com.cnusw.balancetalk.domain.game.controller.request.GameRequest;
import com.cnusw.balancetalk.domain.game.controller.response.CategoryGamesResponse;
import com.cnusw.balancetalk.domain.game.controller.response.GameResponse;
import com.cnusw.balancetalk.domain.game.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "game", description = "게임 API")
@RestController
@RequiredArgsConstructor
public class GameRestController {

    private final GameService gameService;

    @GetMapping("/")
    @Operation(summary = "메인페이지", description = "여러 카테고리의 게임을 리스트로 보여준다")
    public List<CategoryGamesResponse> getCategoryGames() {
        return gameService.getCategoryGamesList();
    }

    @PostMapping("/games/create")
    @Operation(summary = "게임 제작", description = "게임을 제작한다.")
    public ResponseEntity<Long> create(@RequestBody GameRequest request, HttpServletRequest servletRequest) {
        return ResponseEntity.ok(gameService.createGame(request, servletRequest));
    }

    @GetMapping("/games/search")
    @Operation(summary = "게임 검색", description = "게임을 검색한다.")
    public List<GameResponse> search(@RequestParam(value = "searchKeyword") String keyword) {
        List<GameResponse> gameResponseList = gameService.searchGames(keyword);
        return gameResponseList;
    }

    @GetMapping("/games")
    @Operation(summary = "모든 게임 조회", description = "모든 게임을 지정한 정렬 방법으로 가져온다.")
    public List<GameResponse> getGames(@Parameter(name = "sortBy", description = "정렬 방법") @RequestParam(name = "sortBy", required = false) String sortBy) {
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
    @Operation(summary = "id에 해당하는 게임 조회", description = "id에 해당하는 게임을 가져온다.")
    public GameResponse findById(@Parameter(name = "game id", description = "게임 아이디") @PathVariable Long id) {
        return gameService.findById(id);
    }

    @PutMapping("/games/{id}/report")
    @Operation(summary = "게임 신고", description = "id에 해당하는 게임을 신고한다.")
    public ResponseEntity<String> reportGame(@Parameter(name = "game id", description = "게임 아이디") @PathVariable Long id) {
        gameService.reportGame(id);
        return ResponseEntity.ok("Game has been reported.");
    }

}

