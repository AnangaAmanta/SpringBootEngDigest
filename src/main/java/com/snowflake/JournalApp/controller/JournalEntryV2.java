package com.snowflake.JournalApp.controller;


import com.snowflake.JournalApp.entity.JournalEntry;
import com.snowflake.JournalApp.entity.User;
import com.snowflake.JournalApp.services.JournalEntryService;
import com.snowflake.JournalApp.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryV2 {

    @Autowired
    JournalEntryService jeServ;

    @Autowired
    UserService userService;


    @GetMapping("/getJournal/{userName}")
    public ResponseEntity<List<JournalEntry>> getAllJournalUser(@PathVariable String userName) {
        User user1 =  userService.findByUserName(userName);

        List<JournalEntry> all = user1.getJournalEntries();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("getJournalId/{JournalId}")
    public ResponseEntity<JournalEntry> getJpournalId(@PathVariable ObjectId JournalId) {
        Optional<JournalEntry> je = jeServ.findById(JournalId);
        if (je.isPresent()) {
            System.out.println("Here");
            return new ResponseEntity<>(je.get(), HttpStatus.OK);
        }
        System.out.println("Here1");

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{userName}/{JournalId}")
    public ResponseEntity<?> delJpournalId(@PathVariable ObjectId JournalId,@PathVariable String userName) {
        jeServ.deleteById(JournalId,userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/createJournal/{userName}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry journal, @PathVariable String userName) {
        try {
            jeServ.saveJournal(journal,userName);
            return new ResponseEntity<>(journal, HttpStatus.CREATED);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }


    }


    @PutMapping("/updateJournal/{userName}/{JournalId}")
    public ResponseEntity<?> updateJournal(@PathVariable ObjectId JournalId, @PathVariable String userName, @RequestBody JournalEntry newJournal) {

        JournalEntry old = jeServ.findById(JournalId).orElse(null);
        // 2. Check and update Title
        String newTitle = newJournal.getTitle();
        String newContent = newJournal.getContent();
        if ((newTitle != null && !newTitle.trim().isEmpty()) && (newContent != null && !newContent.trim().isEmpty()) && old != null) {
            old.setTitle(newTitle);
            old.setContent(newContent);
            jeServ.saveJournal(old);
            return new ResponseEntity<>(old, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

    }

}
