package com.icy9.service;


import com.icy9.encryption.DesHelper;
import com.icy9.entity.User;
import com.icy9.repository.UserRepository;
import com.icy9.token.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    private UserService userService;
    private TokenUtil tokenUtil;
    private UserRepository userRepository;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    public LoginServiceImpl(
            UserService userService,
            TokenUtil tokenUtil,
            UserRepository userRepository) {
        this.userService = userService;
        this.tokenUtil = tokenUtil;
        this.userRepository = userRepository;
    }

    @Override
    public User register(User userToAdd) {
        final String username = userToAdd.getUsername();
        if(userRepository.findByUsername(username)!=null) {
            return null;
        }
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = userToAdd.getPassword();
        userToAdd.setPassword(DesHelper.encode(rawPassword));
//        userToAdd.setLastPasswordResetDate(new Date());
//        userToAdd.setRoles(asList("ROLE_USER"));
        return userRepository.save(userToAdd);
    }

    @Override
    public User login(String username, String password) {
        // Reload password post-security so we can generate token
        User user = userService.getUserByUsername(username);
        if (user != null)
        {
            final String token = tokenUtil.generateToken(user);
            user.setToken(token);
            return user;
        }
        return  null;

    }

    @Override
    public String refresh(String oldToken) {
        if (oldToken != null)
        {
            final String token = oldToken.substring(tokenHead.length());
            String username = tokenUtil.getUsernameFromToken(token);
            User user = (User) userService.getUserByUsername(username);
            if (tokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())){
                return tokenUtil.refreshToken(token);
            }
        }
        return null;
    }
}
