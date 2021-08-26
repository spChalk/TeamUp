package com.example.socialnetworkingapp.model.job_application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;

    public List<JobApplication> findApplicationsByUserId(Long id) {
        return this.jobApplicationRepository.findAllApplicationsByUserId(id);
    }

    public List<JobApplication> findApplicationsByJobId(Long id) {
        return this.jobApplicationRepository.findAllApplicationsByJobId(id);
    }

    public JobApplication addJobApplication(JobApplication jobApplication) {
        return this.jobApplicationRepository.save(jobApplication);
    }

    public void deleteJobApplication(Long id) {
        this.jobApplicationRepository.deleteById(id);
    }
}