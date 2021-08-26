package com.example.socialnetworkingapp.model.job;

import com.example.socialnetworkingapp.model.job.Job;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
@AllArgsConstructor
public class JobController {

    private final JobService jobService;

    @GetMapping("/{job_id}")
    public Job getJobById(@PathVariable("job_id") Long id){
        return jobService.findJobById(id);
    }

    @GetMapping("/all")
    public List<Job> getJobs(){
        return jobService.getJobs();
    }

    @PostMapping("/add")
    public ResponseEntity<Job> addJob(@RequestBody Job job) {
        Job newJob = jobService.addJob(job);
        return new ResponseEntity<>(newJob, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Job> updateJob(@RequestBody Job job){
        Job newJob = jobService.updateJob(job);
        return new ResponseEntity<>(newJob, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteJobById(@PathVariable("id") Long id){
        jobService.deleteJob(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}