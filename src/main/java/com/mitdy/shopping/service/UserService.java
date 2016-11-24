package com.mitdy.shopping.service;

import java.util.List;

import com.mitdy.shopping.domain.User;

public interface UserService {

    public User saveUser(User user);

    public List<User> findAllUsers();

}
