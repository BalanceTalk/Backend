package com.cnusw.balancetalk.domain.game.service.Dto;


import com.cnusw.balancetalk.domain.comment.entity.Comment;
import com.cnusw.balancetalk.domain.option.entity.Option;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameDto {
    private Long game_id;
    private String user_id;
    private String title;
    private LocalDateTime deadline;
    private long playerCount;
    private long likes;
    private List<Option> options;
    private List<Comment> comments;
}
