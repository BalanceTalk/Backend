package com.cnusw.balancetalk.domain.game.controller.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.cnusw.balancetalk.domain.game.entity.Game;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameResponse {
    private Long game_id;
    private Long user_id;
    private String title;
    private long playerCount;
    private long likes;

    public static GameResponse from(Game game) {
        return GameResponse.builder()
                .game_id(game.getId())
                .title(game.getTitle())
                .playerCount(game.getPlayerCount())
                .likes(game.getLikes())
                .build();
    }
}
