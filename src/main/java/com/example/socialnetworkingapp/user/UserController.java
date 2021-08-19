package com.example.socialnetworkingapp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String welcomeGuest() {
        return ("<!doctype html>\n" +
                "<html>\n" +
                "  <head>\n" +
                "    <title>Welcome!</title>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <h1>Welcome!</h1>\n" +
                "  </body>\n" +
                "</html>");
    }

    @GetMapping(path = "/home")
    public String home(){
        return ("<!doctype html>\n" +
                "<html>\n" +
                "  <head>\n" +
                "    <title>Home</title>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <h1>You are logged in.</h1>\n" +
                "  </body>\n" +
                "</html>");
    }

    @PostMapping(path = "/register")
    public void registerNewUser(@RequestBody Usr user) {
        userService.addNewUser(user);
    }

    @PostMapping(path = "/login")
    public void logUser(
            @RequestParam(required = true) String email,
            @RequestParam(required = true) String password
    ) { userService.logUser(email, password); }
}
