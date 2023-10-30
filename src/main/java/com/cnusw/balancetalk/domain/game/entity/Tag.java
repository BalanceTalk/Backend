package com.cnusw.balancetalk.domain.game.entity;


import com.cnusw.balancetalk.global.common.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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

}
