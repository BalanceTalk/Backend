package com.cnusw.balancetalk.domain.option.service.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OptionDto {
    private Long Option_id;
    private String title;
    private String description;
    private String imgUrl;
}