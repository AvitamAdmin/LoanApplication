package com.avitam.bankloanapplication.core.service;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}

