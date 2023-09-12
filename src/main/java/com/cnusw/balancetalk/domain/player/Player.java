package com.cnusw.balancetalk.domain.player;

import java.util.ArrayList;
import java.util.List;

import com.cnusw.balancetalk.domain.comment.Comment;
import com.cnusw.balancetalk.domain.game.Game;
import com.cnusw.balancetalk.domain.vote.Vote;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Player {

    @Id
    @GeneratedValue
    @Column(name = "player_id")
    private Long id;

    @Column(nullable = false)
    private String email;

    private String nickname;

    @Column(nullable = false)
    private String password;

    private int age;

    @Enumerated(EnumType.STRING)
    private Region region;

    @OneToMany(mappedBy = "player")
    private List<Game> games = new ArrayList<>();

    @OneToMany(mappedBy = "player")
    private List<Comment> comments = new ArrayList<>();

    @OneToOne(mappedBy = "player")
    private Vote vote;
}
