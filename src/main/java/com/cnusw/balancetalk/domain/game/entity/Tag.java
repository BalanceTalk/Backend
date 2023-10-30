package com.cnusw.balancetalk.domain.game.entity;


import com.cnusw.balancetalk.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class Tag extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name="tag_id")
    private Long id;

    @Column(nullable = false, length = 10)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private  Game game;

}
