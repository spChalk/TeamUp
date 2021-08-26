package com.example.socialnetworkingapp.model.job;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class JobService {

    private final JobRepository jobRepository;

    public Job findJobById(Long id) {
        return this.jobRepository.findById(id).
                orElseThrow(() -> new IllegalStateException("Job with id " + id + " was not found!" ));
    }

    public List<Job> getJobs() {
        return this.jobRepository.findAll();
    }

    public Job addJob(Job job) {
        return this.jobRepository.save(job);
    }

    public Job updateJob(Job job) {
        return this.jobRepository.save(job);
    }

    public void deleteJob(Long id) {
        this.jobRepository.deleteById(id);
    }
}