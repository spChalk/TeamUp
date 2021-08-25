package com.example.socialnetworkingapp.model.job_application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;


}