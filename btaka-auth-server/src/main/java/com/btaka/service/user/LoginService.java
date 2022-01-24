package com.btaka.service.user;

public interface LoginService {

    boolean auth(String userId, String password);

    boolean isLogin(String sessionId);
}
