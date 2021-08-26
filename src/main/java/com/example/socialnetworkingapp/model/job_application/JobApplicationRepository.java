package com.example.socialnetworkingapp.model.job_application;

import com.example.socialnetworkingapp.model.bio.Bio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    @Query("SELECT ja FROM JobApplication ja WHERE ja.user.id = ?1")
    List<JobApplication> findAllApplicationsByUserId(Long id);

    @Query("SELECT ja FROM JobApplication ja WHERE ja.job.id = ?1")
    List<JobApplication> findAllApplicationsByJobId(Long id);
}
