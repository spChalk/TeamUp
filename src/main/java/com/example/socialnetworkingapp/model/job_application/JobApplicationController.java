package com.example.socialnetworkingapp.model.job_application;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobapp")
@AllArgsConstructor
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;

    @GetMapping("/{user_id}")
    public List<JobApplication> getApplicationsByUserId(@PathVariable("user_id") Long id){
        return jobApplicationService.findApplicationsByUserId(id);
    }

    @GetMapping("/{job_id}")
    public List<JobApplication> getApplicationsByJobId(@PathVariable("job_id") Long id){
        return jobApplicationService.findApplicationsByJobId(id);
    }

    @PostMapping("/apply")
    public ResponseEntity<JobApplication> addJobApplication(@RequestBody JobApplication jobApplication){
        JobApplication newJobApplication = jobApplicationService.addJobApplication(jobApplication);
        return new ResponseEntity<>(newJobApplication, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteJobApplicationById(@PathVariable("id") Long id){
        jobApplicationService.deleteJobApplication(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}