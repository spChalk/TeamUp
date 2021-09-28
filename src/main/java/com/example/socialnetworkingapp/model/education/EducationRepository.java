package com.example.socialnetworkingapp.model.education;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface EducationRepository extends JpaRepository<Education, Long> {
}
