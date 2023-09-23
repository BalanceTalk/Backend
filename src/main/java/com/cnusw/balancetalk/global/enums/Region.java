package com.cnusw.balancetalk.global.enums;

public enum Region {

    SEOUL("서울특별시"),
    SEJONG("세종특별자치시"),
    INCHEON("인천광역시"),
    DAEJEON("대전광역시"),
    GWANGJU("광주광역시"),
    DAEGU("대구광역시"),
    ULSAN("울산광역시"),
    BUSAN("부산광역시"),
    GYEONGGI("경기도"),
    GANGWON("강원도"),
    CHUNGBUK("충청북도"),
    CHUNGNAM("충청남도"),
    JEONBUK("전라북도"),
    JEONNAM("전라남도"),
    GYEONGBUK("경상북도"),
    GYEONGNAM("경상북도"),
    JEJU("제주특별자치도");

    private String name;

    Region(String name) {
        this.name = name;
    }
}
