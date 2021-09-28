package com.example.socialnetworkingapp.model.experience;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ExperienceRepository extends JpaRepository<Experience, Long> {
}
