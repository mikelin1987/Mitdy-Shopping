package com.mitdy.shopping.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mitdy.shopping.domain.User;
import com.mitdy.shopping.service.UserService;

@Controller
@RequestMapping
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/user-list")
    public String userList(Model model) {
        List<User> users = userService.findAll();
        System.out.println(users);
        model.addAttribute("users", users);
        return "user-list";
    }

    @RequestMapping("/getUsers")
    @ResponseBody
    public ResponseResult<List<User>> getUsers(Model model) throws InterruptedException {
        Thread.sleep(3000);
        List<User> users = userService.findAll();
        return new ResponseResult<List<User>>(users);
    }

}
