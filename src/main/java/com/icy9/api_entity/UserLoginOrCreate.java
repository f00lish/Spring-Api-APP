package com.icy9.api_entity;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by f00lish on 2017/7/4.
 * Qun:530350843
 */
public class UserLoginOrCreate {
    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
