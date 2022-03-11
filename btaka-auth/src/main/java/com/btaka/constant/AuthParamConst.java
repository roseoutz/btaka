package com.btaka.constant;

public enum AuthParamConst {
    // auth
    PARAM_AUTH_SESSION_ID("psid"),
    PARAM_AUTH_ACCESS_TOKEN("accessToken"),
    PARAM_AUTH_IS_LOGIN("isLogin"),

    // oauth
    PARAM_OAUTH_CLIENT_ID("client_id"),
    PARAM_OAUTH_CLIENT_SECRET("client_secret"),
    PARAM_OAUTH_RESPONSE_TYPE("response_type"),
    PARAM_OAUTH_GRANT_TYPE("grant_type"),
    PARAM_OAUTH_STATE("state"),
    PARAM_OAUTH_REDIRECT_URI("redirect_uri"),
    PARAM_OAUTH_REDIRECT_URL("redirect_url"),
    PARAM_OAUTH_SCOPE("scope"),
    PARAM_OAUTH_AUTHORIZATION_CODE("code"),
    PARAM_OAUTH_HEADER_TOKEN_KEY("Authorization"),
    PARAM_OAUTH_TOKEN_TYPE("token_type"),
    PARAM_OAUTH_ACCESS_TOKEN("access_token"),
    ;

    private final String key;

    AuthParamConst(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}
