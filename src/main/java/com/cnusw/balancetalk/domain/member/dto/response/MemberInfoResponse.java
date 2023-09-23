package com.cnusw.balancetalk.domain.member.dto.response;

import com.cnusw.balancetalk.domain.member.entity.Member;
import com.cnusw.balancetalk.domain.member.enums.Region;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class MemberInfoResponse {

    private String email;
    private String nickname;
    private int age;
    private Region region;

    public static MemberInfoResponse from(Member member) {
        return MemberInfoResponse.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .age(member.getAge())
                .region(member.getRegion())
                .build();
    }
}
