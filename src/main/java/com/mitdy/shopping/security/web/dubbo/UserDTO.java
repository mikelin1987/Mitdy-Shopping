package com.mitdy.shopping.security.web.dubbo;

import java.io.Serializable;

public class UserDTO implements Serializable {

    private static final long serialVersionUID = 5865518758370061972L;

    private Long id;
    private String username;
    private String nickname;
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
