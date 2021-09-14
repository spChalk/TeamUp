package com.example.socialnetworkingapp.model.interests.account_tags;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class AccountInterestsService {

    private final AccountInterestsRepository interestsRepository;

    public List<AccountInterest> findInterestsById(Long id) {
        return this.interestsRepository.findAllById(Collections.singleton(id));
    }

    public AccountInterest addTag(AccountInterest interest) {
        return this.interestsRepository.save(interest);
    }
}