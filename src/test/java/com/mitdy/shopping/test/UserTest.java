package com.mitdy.shopping.test;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mitdy.shopping.domain.User;
import com.mitdy.shopping.service.UserService;

public class UserTest {

    private UserService userService;

    @Before
    public void before() {
        @SuppressWarnings("resource")
        ApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { "classpath:spring/spring-service.xml", "classpath:spring/spring-persistence.xml" });
        userService = (UserService) context.getBean("userServiceImpl");
    }

    @Test
    public void addUser() {
        for (int i = 10; i < 20; i++) {
            User user = new User("user" + (i + 1), "User" + (i + 1), "user" + (i + 1), new Date(), "admin", "ACTIVE");
            System.out.println(userService.addUser(user));
        }
    }

    @Test
    public void addUser2() {
        int i = 24;
        User user = new User("user" + (i + 1), "User" + (i + 1), "user" + (i + 1), new Date(), "admin", "ACTIVE");
        userService.addUser2(user);
    
    }

    @Test
    public void findUsers() {
        try {
            List<User> users = userService.findAll();

            System.out.println(users.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}