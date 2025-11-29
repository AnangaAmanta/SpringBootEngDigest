package com.snowflake.JournalApp.controller;


import com.snowflake.JournalApp.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/_journal")
public class JournalEntryCO {

    private Map<Object, JournalEntry> jopurnalEntries = new HashMap<>();

    @GetMapping("/getJournal")
    public List<JournalEntry> getAll() {
        return new ArrayList<>(jopurnalEntries.values());
    }

    @GetMapping("id/{JournalId}")
    public JournalEntry getJpournalId(@PathVariable Object JournalId) {
        return jopurnalEntries.get(JournalId);
    }

    @DeleteMapping("id/{JournalId}")
    public JournalEntry delJpournalId(@PathVariable Long JournalId) {
        return jopurnalEntries.remove(JournalId);
    }

    @PostMapping("/createJournal")
    public void createEntry(@RequestBody JournalEntry journal) {

        jopurnalEntries.put(journal.getId(), journal);
    }


    @PutMapping("/updateJournal/{JournalId}")
    public JournalEntry updateJournal(@PathVariable String JournalId, @RequestBody JournalEntry journal) {

        return jopurnalEntries.put(JournalId, journal);
    }

}
