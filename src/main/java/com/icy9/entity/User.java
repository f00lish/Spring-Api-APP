package com.icy9.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by f00lish on 2017/7/4.
 * Qun:530350843
 */
@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }) })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String image;//头像
    private String im_user;
    private String im_passwd;
    private Date im_cratedDate;
    private Date cratedDate;
    private Date lastPasswordResetDate;
    private int enabled;
    @Transient
    @JsonIgnore
    private List<UserAuthority> roles;
    @Transient
    private String token;

    public User() {

    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @JsonIgnore
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }
    @JsonIgnore
    public String getIm_user() {
        return im_user;
    }

    public void setIm_user(String im_user) {
        this.im_user = im_user;
    }
    @JsonIgnore
    public Date getIm_cratedDate() {
        return im_cratedDate;
    }

    public void setIm_cratedDate(Date im_cratedDate) {
        this.im_cratedDate = im_cratedDate;
    }

    public Date getCratedDate() {
        return cratedDate;
    }

    public void setCratedDate(Date cratedDate) {
        this.cratedDate = cratedDate;
    }

    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Date lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }
    @JsonIgnore
    public String getIm_passwd() {
        return im_passwd;
    }

    public void setIm_passwd(String im_passwd) {
        this.im_passwd = im_passwd;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<UserAuthority> getRoles() {
        return roles;
    }

    public void setRoles(List<UserAuthority> roles) {
        this.roles = roles;
    }

}
