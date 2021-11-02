package com.demo.cricket.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    @GetMapping("/")
    public String homePage()
    {
        return "Welcome!";
    }

}
