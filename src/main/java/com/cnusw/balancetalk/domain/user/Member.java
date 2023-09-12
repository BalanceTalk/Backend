package com.cnusw.balancetalk.domain.user;

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
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String email;

    private String nickname;

    @Column(nullable = false)
    private String password;

    private int age;

    @Enumerated(EnumType.STRING)
    private Region region;

    @OneToMany(mappedBy = "member")
    private List<Game> games = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();

    @OneToOne(mappedBy = "member")
    private Vote vote;
}
