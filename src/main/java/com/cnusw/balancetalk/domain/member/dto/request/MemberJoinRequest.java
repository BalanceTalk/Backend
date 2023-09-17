package com.cnusw.balancetalk.domain.member.dto.request;

import com.cnusw.balancetalk.global.enums.Region;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Getter;

@Getter
public class MemberJoinRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 8, max = 16)
    private String password;

    private String nickname;

    @Min(8) @Max(100)
    private int age;

    private Region region;
}
