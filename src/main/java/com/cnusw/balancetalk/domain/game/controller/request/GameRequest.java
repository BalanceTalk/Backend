package com.cnusw.balancetalk.domain.game.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 게임 제작시 필요한 정보들 담아서 보내기
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GameRequest {
    @NotNull
    private String title;
    //@NotNull
    //private LocalDateTime deadline;
    //@NotNull
    //private List<Option> options;
}
