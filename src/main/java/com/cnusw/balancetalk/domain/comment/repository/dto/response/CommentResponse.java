package com.cnusw.balancetalk.domain.comment.repository.dto.response;

import com.cnusw.balancetalk.domain.comment.entity.Comment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentResponse {

    private String content;

    public static CommentResponse of(Comment comment) {
        return CommentResponse.builder()
                .content(comment.getContent())
                .build();
    }

    public static CommentResponse deactivatedOf(Comment comment) {
        return CommentResponse.builder()
                .content(comment.getDeletedContent())
                .build();
    }

}
