
package com.example.socialnetworkingapp.controller;

        import org.springframework.stereotype.Controller;
        import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class LogInController {

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
