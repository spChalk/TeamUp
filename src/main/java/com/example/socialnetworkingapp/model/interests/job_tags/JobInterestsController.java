package com.example.socialnetworkingapp.model.interests.job_tags;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/job_tags")
@AllArgsConstructor
public class JobInterestsController {

    private final JobInterestsService interestsService;

    @PostMapping("/add")
    public ResponseEntity<JobInterest> addTag(@RequestBody JobInterest interest){
        return new ResponseEntity<>(this.interestsService.addTag(interest), HttpStatus.CREATED);
    }
}