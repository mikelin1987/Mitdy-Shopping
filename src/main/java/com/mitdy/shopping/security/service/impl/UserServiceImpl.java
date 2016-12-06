package com.mitdy.shopping.security.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mitdy.shopping.security.domain.User;
import com.mitdy.shopping.security.persistence.UserDao;
import com.mitdy.shopping.security.service.UserService;

@Transactional
@Service("userService")
public class UserServiceImpl implements UserService {
    
    public static final Logger logger = LoggerFactory.getLogger(UserService.class);

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
    
    @Override
    public User findUserById(Long userId) {
        logger.info("findUserById: {}", userId);
        return userDao.findById(userId);
    }
    
    @Override
    public User findUserByUsername(String username) {
        logger.info("findUserByUsername: {}", username);
        return userDao.findByUsername(username);
    }

    @Override
    public User findUserByUsernameNotUseCache(String username) {
        logger.info("findUserByUsernameNotUseCache: {}", username);
        return userDao.findByUsername(username);
    }

}
