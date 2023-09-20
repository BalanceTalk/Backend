package com.cnusw.balancetalk.domain.game.controller.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
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
}
