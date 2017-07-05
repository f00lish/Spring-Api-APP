package com.icy9.service;


import com.icy9.entity.User;
import com.icy9.repository.UserRepository;
import com.icy9.token.TokenUserDetails;
import com.icy9.token.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private TokenUtil tokenUtil;
    private UserRepository userRepository;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    public LoginServiceImpl(
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            TokenUtil tokenUtil,
            UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.tokenUtil = tokenUtil;
        this.userRepository = userRepository;
    }

    @Override
    public User register(User userToAdd) {
        final String username = userToAdd.getUsername();
        if(userRepository.findByUsername(username)!=null) {
            return null;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = userToAdd.getPassword();
        userToAdd.setPassword(encoder.encode(rawPassword));
//        userToAdd.setLastPasswordResetDate(new Date());
//        userToAdd.setRoles(asList("ROLE_USER"));
        return userRepository.save(userToAdd);
    }

    @Override
    public User login(String username, String password) {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        // Perform the security
        try
        {
            final Authentication authentication = authenticationManager.authenticate(upToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }catch (Exception be)
        {
            return null;
        }
        // Reload password post-security so we can generate token
        TokenUserDetails userDetails = (TokenUserDetails)userDetailsService.loadUserByUsername(username);
        final User user = userDetails.getUser();
        if (user != null && user.getIm_cratedDate() == null)
        {
            try {
//                IMChatUtil.im_register(user,new CallbackAdaptor<String>(){
//                    @Override
//                    public DataHandler<String> getDataHandler() {
//                        return StringDataHandler.create();
//                    }
//                    @Override
//                    public void onSuccess(String data) {
//                        //data就是经过处理后的数据，直接在这里写自己的业务逻辑
//                        if (data != null && data.contains("created"))
//                        {
//                            user.setIm_cratedDate(new Date());
//                            userRepository.save(user);
//                        }
//                    }
//                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        final String token = tokenUtil.generateToken(userDetails);
        user.setToken(token);
        return user;
    }

    @Override
    public String refresh(String oldToken) {
        if (oldToken != null)
        {
            final String token = oldToken.substring(tokenHead.length());
            String username = tokenUtil.getUsernameFromToken(token);
            TokenUserDetails user = (TokenUserDetails) userDetailsService.loadUserByUsername(username);
            if (tokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())){
                return tokenUtil.refreshToken(token);
            }
        }
        return null;
    }
}
