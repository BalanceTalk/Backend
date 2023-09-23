package com.cnusw.balancetalk.domain.vote;

import com.cnusw.balancetalk.domain.member.Member;
import com.cnusw.balancetalk.domain.option.entity.Option;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Vote {

    @Id
    @GeneratedValue
    @Column(name = "vote_id")
    private Long id;

    private boolean isVotedFirstOption;
    private boolean isVotedSecondOption;

    private Long voterId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
    private Option option;
}
