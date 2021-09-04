
package com.example.socialnetworkingapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.security.RolesAllowed;

@RestController
public class LogInController {

    @RolesAllowed({"USER","ADMIN"})
    @RequestMapping("/admin")
    public String getAdmin() {
        return "Welcome Admin";
    }
}

