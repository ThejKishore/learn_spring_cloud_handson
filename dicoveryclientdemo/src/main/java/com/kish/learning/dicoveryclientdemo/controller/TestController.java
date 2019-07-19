package com.kish.learning.dicoveryclientdemo.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/hello")
    public String helloWorld(){
        return  "hello World";
    }
}
