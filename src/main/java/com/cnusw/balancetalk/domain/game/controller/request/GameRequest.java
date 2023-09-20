package com.cnusw.balancetalk.domain.game.controller.request;

import com.cnusw.balancetalk.domain.option.entity.Option;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;


/**
 * 게임 제작시 필요한 정보들 담아서 보내기
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GameRequest {
    @NotNull
    private String title;
    @NotNull
    private LocalDateTime deadline;

    //Option1, Option2 단일필드로 분리


}
