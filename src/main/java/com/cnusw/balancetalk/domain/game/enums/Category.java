package com.cnusw.balancetalk.domain.game.enums;

public enum Category {

    POPULARITY("좋아요순"),
    VIEWS("조회수순"),
    LATEST("최신순"),
    GOLDENBALANCE("황금밸런스순");

    private String name;

    Category(String name) {
        this.name = name;
    }
}
