package com.icy9.service;

import com.icy9.entity.User;

public interface LoginService {
    User register(User userToAdd);
    User login(String username, String password);
    String refresh(String oldToken);
}
