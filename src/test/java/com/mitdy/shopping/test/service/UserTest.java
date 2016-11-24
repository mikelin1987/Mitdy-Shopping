package com.mitdy.shopping.test.service;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.mitdy.shopping.security.domain.User;
import com.mitdy.shopping.security.domain.enumuration.UserStatus;
import com.mitdy.shopping.test.base.BaseTest;

public class UserTest extends BaseTest {

    @Test
    public void addUserTest() {
        for (int i = 10; i < 20; i++) {
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
    public void findUsersTest() {
        List<User> users = userService.findAllUsers();
        logger.info("findUsersTest user size: {}", users.size());
    }

}