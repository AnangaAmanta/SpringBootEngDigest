package com.snowflake.JournalApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyClass {

    @Autowired
    Car c;

    @GetMapping("abc")
    public String sayHello(){
       return c.startCar();
    }
}
