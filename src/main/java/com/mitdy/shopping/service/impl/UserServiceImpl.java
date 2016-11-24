package com.mitdy.shopping.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mitdy.shopping.domain.User;
import com.mitdy.shopping.persistence.UserDao;
import com.mitdy.shopping.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User addUser(User user) {
        user = userDao.save(user);
        return user;
    }
    
    @Override
    public User addUser2(User user) {
        user = userDao.save(user);
        if (true) {
            throw new RuntimeException("Just runtime exception.");
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

}
