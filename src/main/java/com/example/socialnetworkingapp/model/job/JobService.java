package com.example.socialnetworkingapp.model.job;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class JobService {

    private final JobRepository jobRepository;

    public com.example.socialnetworkingapp.model.job.Job findJobById(Long id) {
        return this.jobRepository.findById(id).
                orElseThrow(() -> new IllegalStateException("Job with id " + id + " was not found!" ));
    }

    public List<com.example.socialnetworkingapp.model.job.Job> getJobs() {
        return this.jobRepository.findAll();
    }

    public com.example.socialnetworkingapp.model.job.Job addJob(com.example.socialnetworkingapp.model.job.Job job) {
        return this.jobRepository.save(job);
    }

    public com.example.socialnetworkingapp.model.job.Job updateJob(com.example.socialnetworkingapp.model.job.Job job) {
        return this.jobRepository.save(job);
    }

    public void deleteJob(Long id) {
        this.jobRepository.deleteById(id);
    }
}