package com.example.socialnetworkingapp.model.experience;

import com.example.socialnetworkingapp.model.bio.Bio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ExperienceRepository extends JpaRepository<Experience, Long> {

    @Query("SELECT e FROM Experience e WHERE e.user.id = ?1")
    Optional<List<Experience>> findAllByUserId(Long id);

    @Query("DELETE FROM Experience e WHERE e.user.id = ?1")
    void deleteByUserId(Long id);
}