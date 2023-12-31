package com.cnusw.balancetalk.domain.member.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cnusw.balancetalk.domain.comment.entity.CommentDislikes;
import com.cnusw.balancetalk.domain.comment.entity.CommentLikes;
import com.cnusw.balancetalk.domain.member.dto.request.MemberJoinRequest;
import com.cnusw.balancetalk.domain.comment.entity.Comment;
import com.cnusw.balancetalk.domain.game.entity.Game;
import com.cnusw.balancetalk.domain.vote.entity.Vote;
import com.cnusw.balancetalk.global.common.BaseTimeEntity;
import com.cnusw.balancetalk.domain.member.enums.Region;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickname;

    private int age;

    @Enumerated(EnumType.STRING)
    private Region region;

    @Builder.Default
    @Setter
    @Getter
    private long level = 1;

    @Builder.Default
    @Setter
    @Getter
    private long exp = 0;

    @Builder.Default
    @Setter
    @Getter
    private long credit = 0;

    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<Game> games = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<CommentLikes> commentLikes = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<CommentDislikes> commentDislikes = new ArrayList<>();

    @OneToOne(mappedBy = "member")
    private Vote vote;

    public static Member from(MemberJoinRequest request) {
        return Member.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .nickname(request.getNickname())
                .age(request.getAge())
                .region(request.getRegion())
                .build();
    }

    // 비밀번호 암호화
    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }
}
