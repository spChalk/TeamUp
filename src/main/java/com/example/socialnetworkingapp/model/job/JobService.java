package com.example.socialnetworkingapp.model.job;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JobService {

    private final JobRepository jobRepository;


}