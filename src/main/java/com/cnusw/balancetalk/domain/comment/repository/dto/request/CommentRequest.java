package com.cnusw.balancetalk.domain.comment.repository.dto.request;

import com.cnusw.balancetalk.domain.comment.entity.Comment;
import com.cnusw.balancetalk.domain.game.entity.Game;
import com.cnusw.balancetalk.domain.member.entity.Member;
import lombok.Data;

@Data
public class CommentRequest {

    private String content;

    public Comment toEntity(Game game, Member member) {
        return Comment.builder()
                .content(content)
                .game(game)
                .member(member)
                .build();
    }
}
