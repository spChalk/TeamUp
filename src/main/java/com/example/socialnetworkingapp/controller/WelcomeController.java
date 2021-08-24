package com.example.socialnetworkingapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeController {

    @GetMapping("/welcome")
    public String welcome(){
        return "welcome";
    }
}
