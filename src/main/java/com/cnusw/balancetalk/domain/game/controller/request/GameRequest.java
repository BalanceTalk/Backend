package com.cnusw.balancetalk.domain.game.controller.request;


import com.cnusw.balancetalk.domain.comment.Comment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 게임 제작시 필요한 정보들 담아서 보내기
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GameRequest {
    //private String user_id;
    @NotNull
    private String title;
    //@NotNull
    //private LocalDateTime deadline;
    //@NotNull
    //private List<Option> options;
}
