package com.cnusw.balancetalk.domain.comment.dto.request;

import com.cnusw.balancetalk.domain.comment.entity.Comment;
import com.cnusw.balancetalk.domain.game.entity.Game;
import com.cnusw.balancetalk.domain.member.Member;
import lombok.Data;

@Data
public class CommentRequest {
    private String content;

    // toDto가 Dto에 있는게 맞나?
    // content만 존재하는 Request, game과 player까지 존재하는 Dto를 나누는게 좋을까,
    // 그냥 서비스에서 game, player 객체를 파라미터로 전달받아서 바로 넣는 방식으로
    // Request 클래스 하나만 두는게 좋을까?

    // toEntity가 Request에 있는게 맞나? Service Interface에 있어야 하나?
    public Comment toEntity(Game game, Member member) {
        return Comment.builder()
                .content(content)
                .game(game)
                .member(member)
                .build();
    }
}
