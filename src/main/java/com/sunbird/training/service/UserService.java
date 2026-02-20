package com.sunbird.training.service;

import com.sunbird.training.entity.User;

public interface UserService {

    void signup(User user);
    User findByUsername(String username);
}
