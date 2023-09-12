package com.cnusw.balancetalk.domain.game;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.cnusw.balancetalk.domain.comment.Comment;
import com.cnusw.balancetalk.domain.option.Option;
import com.cnusw.balancetalk.domain.player.Player;
import com.cnusw.balancetalk.global.common.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Game extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "game_id")
    private Long id;

    @Column(nullable = false, length = 30)
    private String title;

    @Column(nullable = false)
    private LocalDateTime deadline;

    private long playerCount;

    private long likes;

    @OneToMany(mappedBy = "game")
    private List<Option> options = new ArrayList<>();

    @OneToMany(mappedBy = "game")
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id")
    private Player player;
}
