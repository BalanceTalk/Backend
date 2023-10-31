package com.cnusw.balancetalk.domain.comment.entity;

import com.cnusw.balancetalk.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class CommentReports {

    @Id @GeneratedValue
    @Column(name = "comment_reports_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
