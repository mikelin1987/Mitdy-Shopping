package com.mitdy.shopping.security.service;

import java.util.List;

import com.mitdy.shopping.security.domain.User;

public interface UserService {

    public User saveUser(User user);

    public List<User> findAllUsers();

}
