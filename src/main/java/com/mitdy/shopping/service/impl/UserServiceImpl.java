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
    public User saveUser(User user) {
        user = userDao.save(user);
        return user;
    }

    @Override
    public List<User> findAllUsers() {
        return userDao.findAll();
    }

}
