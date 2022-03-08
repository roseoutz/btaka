package com.btaka.constant;

public enum Social {
    KAKAO("kakao"),
    GITHUB("github"),
    GOOGLE("google"),
    FACEBOOK("facebook");

    private final String name;
    private Social(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
