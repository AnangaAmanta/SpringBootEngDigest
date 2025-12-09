package com.snowflake.JournalApp.controller;


import com.snowflake.JournalApp.config.SpringSecurity;
import com.snowflake.JournalApp.entity.JournalEntry;
import com.snowflake.JournalApp.entity.User;
import com.snowflake.JournalApp.services.JournalEntryService;
import com.snowflake.JournalApp.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserCO {

    @Autowired
    UserService userService;


    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        System.out.println(userName);
        User userdb = userService.findByUserName(userName);
        if(userdb !=null){
            userdb.setUserName(user.getUserName());
            userdb.setPassword(user.getPassword());
            userService.saveUser(userdb);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
