package com.icy9.validator;

import com.icy9.api_entity.UserLoginOrCreate;
import com.icy9.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by f00lish on 2017/7/4.
 * Qun:530350843
 */
@Component
public class UserCreateValidator implements Validator{

    private static final Logger LOGGER = LoggerFactory.getLogger(UserCreateValidator.class);
    private final UserService userService;

    @Autowired
    public UserCreateValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UserLoginOrCreate.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LOGGER.debug("Validating {}", target);
        UserLoginOrCreate form = (UserLoginOrCreate) target;
        // validatePasswords(errors, form);
        validateUsername(errors, form);
    }

    private void validateUsername(Errors errors, UserLoginOrCreate form) {
        if (userService.getUserByUsername(form.getUsername()) != null) {
            errors.reject("username.exists", "User with this USERNAME already exists");
        }
    }
}
