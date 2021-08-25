package com.example.socialnetworkingapp.model.interests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/interests")
public class InterestsController {

    private final InterestsService interestsService;

    @Autowired
    public InterestsController(InterestsService interestsService) {
        this.interestsService = interestsService;
    }

}