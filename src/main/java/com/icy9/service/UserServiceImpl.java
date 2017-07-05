package com.icy9.service;

import com.icy9.entity.User;
import com.icy9.entity.UserAuthority;
import com.icy9.api_entity.UserLoginOrCreate;
import com.icy9.repository.UserAuthorityRepository;
import com.icy9.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by f00lish on 2017/7/4.
 * Qun:530350843
 */
@Service
public class UserServiceImpl implements UserService{

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final UserAuthorityRepository authorityRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserAuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }
    /**
     * 用户名查找
     */
    @Override
    public User getUserByUsername(String username) {
        LOGGER.debug("finding username={}", username);
        User user = userRepository.findByUsername(username);
        if (user != null)
            user.setRoles(authorityRepository.findByUsername(username));
        return user;
    }

    /**
     * 创建新用户
     */
    @Override
    public User create(UserLoginOrCreate userCreate) {
        User user = new User();
        user.setUsername(userCreate.getUsername());
        user.setPassword(new BCryptPasswordEncoder(10).encode(userCreate.getPassword()));
        user.setIm_user(userCreate.getUsername());
        user.setIm_passwd(new Md5PasswordEncoder().encodePassword(userCreate.getPassword(),10));
        user.setEnabled(1);
        user.setCratedDate(new Date());
        user.setLastPasswordResetDate(new Date());

        UserAuthority authority = new UserAuthority();
        authority.setUsername(userCreate.getUsername());
        authority.setAuthority("ROLE_USER");
        authority = authorityRepository.save(authority);
        if (authority != null)
        {
            user = userRepository.save(user);
            LOGGER.debug("save username={}", user);
            return user;
        }
        return null;
    }
}
