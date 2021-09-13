package com.example.socialnetworkingapp.model.interests.job_tags;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class JobInterestsService {

    private final JobInterestsRepository interestsRepository;

    public JobInterest addTag(JobInterest interest) {
        return this.interestsRepository.save(interest);
    }
}