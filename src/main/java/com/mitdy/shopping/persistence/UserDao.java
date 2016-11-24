package com.mitdy.shopping.persistence;

import java.util.List;

import com.mitdy.shopping.domain.User;

public interface UserDao {
    
    public User save(User user);

    public List<User> findAll();
    
}
