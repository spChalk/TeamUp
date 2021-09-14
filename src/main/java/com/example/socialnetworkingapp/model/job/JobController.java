package com.example.socialnetworkingapp.model.job;

import com.example.socialnetworkingapp.model.tags.Tag;
import lombok.AllArgsConstructor;
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

    /* Get the list of jobs, ideal for user with id <<uid>> */
    @GetMapping("/all/{uid}")
    public List<Job> getJobs(@PathVariable("uid") Long uid){
        return jobService.getJobs(uid);
    }

    @PostMapping("/add")
    public ResponseEntity<Job> addJob(@RequestBody Job job) {
        return new ResponseEntity<>(jobService.addJob(job), HttpStatus.CREATED);
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

    @PostMapping("/tags/add")
    public ResponseEntity<HttpStatus>addTag(@RequestBody Job job, Tag tag) {
        this.jobService.addTag(job, tag);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}