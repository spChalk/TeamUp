
package com.example.socialnetworkingapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.security.RolesAllowed;

@RestController
public class LogInController {

    @RolesAllowed("GUEST")
    @RequestMapping("/register")
    public String getGuest() {
        return "Welcome Guest";
    }

    @RolesAllowed("USER")
    @RequestMapping("/users")
    public String getUser() {
        return "Welcome User";
    }

    @RolesAllowed({"USER","ADMIN"})
    @RequestMapping("/admin")
    public String getAdmin() {
        return "Welcome Admin";
    }
}

