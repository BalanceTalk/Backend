package com.cnusw.balancetalk.domain.game.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

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
    private String optionTitle1;
    private String optionDescription1;
    private String optionImgUrl1;

    private String optionTitle2;
    private String optionDescription2;
    private String optionImgUrl2;



}
