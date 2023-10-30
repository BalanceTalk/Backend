package com.cnusw.balancetalk.domain.game.controller.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.cnusw.balancetalk.domain.game.entity.Game;
import com.cnusw.balancetalk.domain.option.entity.Option;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameResponse {
    private String title;
    private long playerCount;
    private long likes;
    private long optionId1;
    private long optionId2;
    private String optionTitle1;
    private String optionTitle2;
    private String optionDescription1;
    private String optionDescription2;
    private String optionImgUrl1;
    private String optionImgUrl2;

    public static GameResponse from(Game game) {
        Option option1 = game.getOptions().get(0);
        Option option2 = game.getOptions().get(1);
        return GameResponse.builder()
                .title(game.getTitle())
                .playerCount(game.getPlayerCount())
                .likes(game.getLikes().size())
                .optionId1(option1.getId())
                .optionId2(option2.getId())
                .optionTitle1(option1.getTitle())
                .optionTitle2(option2.getTitle())
                .optionDescription1(option1.getDescription())
                .optionDescription2(option2.getDescription())
                .optionImgUrl1(option1.getImgUrl())
                .optionImgUrl2(option2.getImgUrl())
                .build();
    }
}
