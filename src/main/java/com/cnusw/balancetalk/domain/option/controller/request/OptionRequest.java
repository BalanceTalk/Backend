package com.cnusw.balancetalk.domain.option.controller.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OptionRequest {
    private String title;
    private String description;
    private String imgUrl;
}
