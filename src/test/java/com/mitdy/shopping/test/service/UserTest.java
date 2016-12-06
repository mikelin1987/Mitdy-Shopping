package com.mitdy.shopping.test.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.data.redis.core.ValueOperations;

import com.mitdy.shopping.security.domain.User;
import com.mitdy.shopping.security.domain.enumuration.UserStatus;
import com.mitdy.shopping.test.base.BaseTest;

public class UserTest extends BaseTest {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    @Test
    public void addUserTest() {
        User user = new User("user002", "User002", "User002", UserStatus.ACTIVE, new Date(), "system");
        user = userService.saveUser(user);
        logger.info("addUserTest generated ID: {}", user.getId());
    }

    @Test
    public void findUsersTest() {
        List<User> users = userService.findAllUsers();
        logger.info("findUsersTest user size: {}", users.size());
    }

    @Test
    public void addLargeDataTest() {
        for (int i = 0; i < 100000; i++) {
            UserStatus status = UserStatus.ACTIVE;
            if (i % 3 == 0) {
                status = UserStatus.ACTIVE;
            } else if (i % 3 == 1) {
                status = UserStatus.INACTIVE;
            } else if (i % 3 == 2) {
                status = UserStatus.LOCKED;
            }
            User user = new User("user" + (i + 1), "User" + (i + 1), "admin", status, new Date(), "user" + (i + 1));
            user = userService.saveUser(user);
            logger.info("addUserTest generated ID: {}", user.getId());
        }
    }

    @Test
    public void findUserByIdTest() {
        User user = userService.findUserById(100014L);
        System.out.println(user);
    }
    

    @Test
    public void findUserByUsernameTest1() {
        User user = userService.findUserByUsername("user002");
        user.setNickname("u002");
        user = userService.saveUser(user);
        System.out.println(user);
    }
    
    @Test
    public void findUserByUsernameTest2() {
        Date time1 = new Date();
        System.out.println("time1: " + SIMPLE_DATE_FORMAT.format(time1));
        
        for (int i = 0; i < 100; i++) {
            User user = userService.findUserByUsername("user002");
            System.out.println(i + ", " + user);
        }
        
        Date time2 = new Date();
        System.out.println("time2: " + SIMPLE_DATE_FORMAT.format(time2));
        
        System.out.println("sknew: " + (time2.getTime() - time1.getTime()));
    }
    
    @Test
    public void findUserByUsernameTest3() {
        Date time1 = new Date();
        System.out.println("time1: " + SIMPLE_DATE_FORMAT.format(time1));
        
        for (int i = 0; i < 100; i++) {
            User user = userService.findUserByUsernameNotUseCache("user002");
            System.out.println(i + ", " + user);
        }
        
        Date time2 = new Date();
        System.out.println("time2: " + SIMPLE_DATE_FORMAT.format(time2));
        
        System.out.println("sknew: " + (time2.getTime() - time1.getTime()));
    }

    @Test
    public void redisTest() {
        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
        System.out.println("Before: " + opsForValue.get("key"));
        opsForValue.set("key", "value");
        
        System.out.println("After: " + opsForValue.get("key"));
    }
    
    @Test
    public void redisTest2() {
        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
        System.out.println("test1: " + opsForValue.get("user_id_100015"));
    }

}