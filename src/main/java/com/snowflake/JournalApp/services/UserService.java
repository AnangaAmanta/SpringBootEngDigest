package com.snowflake.JournalApp.services;

import com.snowflake.JournalApp.entity.JournalEntry;
import com.snowflake.JournalApp.entity.User;
import com.snowflake.JournalApp.repository.JournalEntryRepo;
import com.snowflake.JournalApp.repository.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    UserRepo userRepo;

    public void saveUser(User user){
        userRepo.save(user);
    }

    public List<User> getAll(){
        return userRepo.findAll();
    }

    public Optional<User> findById(ObjectId id){
        return userRepo.findById(id);
    }



    public void deleteById(ObjectId id){
        userRepo.deleteById(id);
    }

    public User findByUserName(String userName) {
        return userRepo.findByUserName(userName);
    }
}
