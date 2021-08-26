package com.example.socialnetworkingapp.model.interests;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class InterestsService {

    private final InterestsRepository interestsRepository;

    public List<Interest> findInterestsById(Long id) {
        return this.interestsRepository.findAllById(Collections.singleton(id));
    }
}