package com.example.socialnetworkingapp.model.interests.job_tags;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface JobInterestsRepository extends JpaRepository<JobInterest, Long> {
}
