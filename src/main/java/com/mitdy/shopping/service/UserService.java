package com.mitdy.shopping.service;

import java.util.List;

import com.mitdy.shopping.domain.User;

public interface UserService {

    public User addUser(User user);
    
    public User addUser2(User user);

    public List<User> findAll();

}
