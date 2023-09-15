package com.cnusw.balancetalk.domain.game.service.Dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameDto {
    private Long gameId;
    private String title;
    private LocalDateTime deadline;
    private long playerCount;
    private long likes;
}
