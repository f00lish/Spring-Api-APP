package com.icy9.service;

import com.icy9.api_entity.UserLoginOrCreate;
import com.icy9.entity.User;

/**
 * Created by f00lish on 2017/7/4.
 * Qun:530350843
 */
public interface UserService {

    User create(UserLoginOrCreate userCreate);

    User getUserByUsername(String username);

}
