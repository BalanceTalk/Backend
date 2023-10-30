package com.cnusw.balancetalk.domain.game.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.cnusw.balancetalk.domain.comment.entity.Comment;
import com.cnusw.balancetalk.domain.option.entity.Option;
import com.cnusw.balancetalk.domain.member.entity.Member;
import com.cnusw.balancetalk.global.common.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class Game extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private Long id;

    @Column(nullable = false, length = 30)
    private String title;

    @Column(nullable = false)
    private LocalDateTime deadline;

    private long playerCount;

    private long likes;

    @Builder.Default
    @OneToMany(mappedBy = "game")
    private List<Option> options = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "game")
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // Add a column for the number of reports
    @Builder.Default
    @Setter
    @Getter
    @Column(nullable = false)
    private long reports = 0;

    // Add a column for whether the game is active or not
    @Builder.Default
    @Setter
    @Column(nullable = false)
    private boolean activation = true;

}
