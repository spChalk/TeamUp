package com.example.socialnetworkingapp.model.experience;

import com.example.socialnetworkingapp.model.bio.Bio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {

    @Query("SELECT Experience FROM Experience WHERE Experience.user.id = ?1")
    Optional<List<Experience>> findAllByUserId(Long id);

    @Query("DELETE FROM Experience WHERE Experience.user.id = ?1")
    void deleteByUserId(Long id);
}
