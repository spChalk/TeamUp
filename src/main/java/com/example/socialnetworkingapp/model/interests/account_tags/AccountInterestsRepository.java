package com.example.socialnetworkingapp.model.interests.account_tags;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface AccountInterestsRepository extends JpaRepository<AccountInterest, Long> {
}
