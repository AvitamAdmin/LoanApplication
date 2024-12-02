package com.avitam.bankloanapplication.service;

import com.avitam.bankloanapplication.model.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAll();

    void delete(String username);
}