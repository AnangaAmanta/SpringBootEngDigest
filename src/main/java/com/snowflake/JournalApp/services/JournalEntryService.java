package com.snowflake.JournalApp.services;

import com.snowflake.JournalApp.entity.JournalEntry;
import com.snowflake.JournalApp.entity.User;
import com.snowflake.JournalApp.repository.JournalEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    JournalEntryRepo repo;
    @Autowired
    UserService userService;

    @Transactional
    public void saveJournal(JournalEntry je, String userName){
        User user =  userService.findByUserName(userName);
        je.setDate(LocalDateTime.now());
        JournalEntry saved = repo.save(je);
        user.getJournalEntries().add(saved);
        userService.saveUser(user);
    }

    public List<JournalEntry> getAll(){
        return repo.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id){
        return repo.findById(id);
    }

    public void deleteById(ObjectId id, String userName){
        User user =  userService.findByUserName(userName);
        user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        userService.saveUser(user);
        repo.deleteById(id);
    }

    public void saveJournal(JournalEntry old) {
        repo.save(old);
    }
}
