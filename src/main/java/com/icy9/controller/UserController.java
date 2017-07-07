package com.icy9.controller;

import com.icy9.entity.Response;
import com.icy9.entity.User;
import com.icy9.api_entity.UserLoginOrCreate;
import com.icy9.repository.UserRepository;
import com.icy9.service.UserService;
import com.icy9.token.TokenValidate;
import com.icy9.validator.UserCreateValidator;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 用户管理API
 * Created by f00lish on 2017/7/4.
 */

@RestController
@RequestMapping(value = "/api", name = "用户管理API")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private UserService userService;
    private UserCreateValidator userCreateValidator;
    private UserRepository userRepository;

    private Response response = new Response();

    @Autowired
    private Environment env;

    @Autowired
    public UserController(UserService userService, UserCreateValidator userCreateValidator,
                          UserRepository userRepository) {
        this.userService = userService;
        this.userCreateValidator = userCreateValidator;
        this.userRepository = userRepository;
    }
    @TokenValidate
    @ApiOperation(value="创建用户", notes="传userCreate进来，包含用户名和密码",response = Response.class)
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public ResponseEntity<Response> userCreate(@Valid @RequestBody UserLoginOrCreate userCreate,
                                                        BindingResult bindingResult) {
        LOGGER.debug("Processing user create ={}, bindingResult={}", userCreate, bindingResult);
        if (bindingResult.hasErrors()) {
            // failed validation
            ObjectError objectError = (ObjectError)bindingResult.getAllErrors().get(bindingResult.getAllErrors().size()-1);
            response.setMsg(Response.FAIL, "用户创建失败: "+ objectError.getDefaultMessage());
            return new ResponseEntity<Response>(response, HttpStatus.OK);
        }
        try {
            final User user = userService.create(userCreate);

        } catch (DataIntegrityViolationException e) {
            String tips= "用户创建失败：用户名已存在";
            LOGGER.warn(tips, e);
            bindingResult.reject("username.exists", tips);
            response.setMsg(Response.FAIL, tips);
            return new ResponseEntity<Response>(response, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
        }
        // ok, redirect
        response.setMsg(Response.OK, "用户创建成功");
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }
}
