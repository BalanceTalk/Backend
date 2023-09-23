package com.cnusw.balancetalk.controller;

import com.cnusw.balancetalk.domain.game.controller.response.GameResponse;
import com.cnusw.balancetalk.domain.game.entity.Game;
import com.cnusw.balancetalk.domain.game.repository.GameRepository;
import com.cnusw.balancetalk.domain.game.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeController {

    private final GameService gameService;
    @Autowired
    private GameRepository gameRepository;

    public HomeController(GameService gameService) {
        this.gameService = gameService;
    }
//    @GetMapping("/home.html") // URL 경로를 변경
//    public String home(Model model) {
//        // H2 Database에서 게임 리스트를 가져옴
//        List<GameResponse> games = gameService.getGameListAll();
//
//        // 모델에 게임 리스트를 추가
//        model.addAttribute("games", games);
//
//        return "home"; // 홈 화면 템플릿 이름
//    }
@GetMapping("/games")
public List<GameResponse> getGames(@RequestParam(name = "sortBy", required = false) String sortBy) {
    List<GameResponse> games;

    if ("popularity".equals(sortBy)) {
        games = gameService.getGamesSortedByPopularity();
    } else if ("views".equals(sortBy)) {
        games = gameService.getGamesSortedByViews();
    } else if ("latest".equals(sortBy)) {
        games = gameService.getGamesSortedByLatest();
    } else {
        // 기본적으로는 인기순으로 정렬된 결과를 반환
        games = gameService.getGamesSortedByPopularity();
    }

    return games;
}
//    @GetMapping
//    public List<GameResponse> home() {
//        // H2 Database에서 게임 리스트를 가져옴
//        List<GameResponse> games = gameService.getGameListAll();
////                gameRepository.findAll();
//        return games;
//
//        // 모델에 게임 리스트를 추가
////        model.addAttribute("games", games);
////
////        return "home"; // 홈 화면 템플릿 이름
//    }
}
