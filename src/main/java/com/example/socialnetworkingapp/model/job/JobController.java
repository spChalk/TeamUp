package com.example.socialnetworkingapp.model.job;

import com.example.socialnetworkingapp.model.account.Account;
import com.example.socialnetworkingapp.model.account.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/jobs")
@AllArgsConstructor
public class JobController {

    private final JobService jobService;
    private final AccountService accountService;

    /* Get the list of jobs for user */
    @GetMapping("/all")
    public List<JobResponse> getJobs() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();
        Account newUser = accountService.findAccountByEmail(user);
        return jobService.getJobs(newUser);
    }

    @PostMapping("/add")
    public ResponseEntity<JobResponse> addJob(@RequestBody JobRequest jobr) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();
        Account me = accountService.findAccountByEmail(user);
        Job newJob = new Job(jobr.getTitle(), me, jobr.getLocation(), new Date(),
                jobr.getJobType(), jobr.getExperienceLevel(), jobr.getInfo(), jobr.getTags());
        return new ResponseEntity<>(jobService.addJob(newJob), HttpStatus.CREATED);
    }

    @PutMapping("/update/{jId}")
    public ResponseEntity<JobResponse> updateJob(@PathVariable("jId") Long jobId,
                                         @RequestBody JobRequest jobr){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();
        Account me = accountService.findAccountByEmail(user);
        return new ResponseEntity<>(jobService.updateJob(jobId, jobr), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteJobById(@PathVariable("id") Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account currUser = accountService.findAccountByEmail(authentication.getName());
        jobService.deleteJob(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}