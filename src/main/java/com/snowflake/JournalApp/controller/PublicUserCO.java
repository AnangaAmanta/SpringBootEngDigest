package com.snowflake.JournalApp.controller;

import com.snowflake.JournalApp.entity.User;
import com.snowflake.JournalApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicUserCO {

    @Autowired
    UserService userService;

    @GetMapping
    public List<User> getAllUser(){
        return userService.getAll();
    }

    @PostMapping("/createUser")
    public void createUser(@RequestBody User user){
        userService.saveUser(user);
    }
}
