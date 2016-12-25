package com.mitdy.shopping.security.web.dubbo;

public interface UserServiceApi {

    public UserDTO findUserByUsername(String username);
    
}
