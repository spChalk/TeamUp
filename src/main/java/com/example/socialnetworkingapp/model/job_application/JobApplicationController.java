package com.example.socialnetworkingapp.model.job_application;

import com.example.socialnetworkingapp.model.account.Account;
import com.example.socialnetworkingapp.model.account.AccountService;
import com.example.socialnetworkingapp.model.job.JobService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobapp")
@AllArgsConstructor
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;
    private final AccountService accountService;
    private final JobService jobService;

   @GetMapping("/u")
    public List<JobApplicationResponse> getApplicationsByUserId(){
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       Account applicant = this.accountService.findAccountByEmail(authentication.getName());
       return jobApplicationService.findApplicationsByUserId(applicant.getId());
    }

    @GetMapping("/j/{job_id}")
    public List<JobApplicationResponse> getApplicationsByJobId(@PathVariable("job_id") Long id){
        return jobApplicationService.findApplicationsByJobId(id);
    }

    @GetMapping("/uj/{user_id}/{job_id}")
    public JobApplicationResponse getApplicationByUserAndJobIds(@PathVariable("user_id") Long userId,
                                                                @PathVariable("job_id") Long jobId) {
        return jobApplicationService.findApplicationByUserAndJobIds(userId, jobId);
    }

    @PostMapping("/apply/{jobId}")
    public ResponseEntity<JobApplicationResponse> addJobApplication(@PathVariable("jobId") Long jobId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account applicant = this.accountService.findAccountByEmail(authentication.getName());
        JobApplication jobApplication = new JobApplication(applicant, this.jobService.findJobById(jobId));
        return new ResponseEntity<>(jobApplicationService.addJobApplication(jobApplication), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteJobApplicationById(@PathVariable("id") Long id){
        jobApplicationService.deleteJobApplication(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}