package com.cnusw.balancetalk.domain.game.controller.response;

import com.cnusw.balancetalk.domain.game.entity.Game;
import com.cnusw.balancetalk.domain.game.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryGamesResponse {
    private Category category;
    private List<GameResponse> gamesInCategory;

    public static CategoryGamesResponse from(List<GameResponse> gameResponses, Category category) {
        return CategoryGamesResponse.builder()
                .gamesInCategory(gameResponses)
                .category(category)
                .build();
    }
}
