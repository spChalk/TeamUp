package com.example.socialnetworkingapp.model.bio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BioRepository extends JpaRepository<Bio, Long> {

    @Query("SELECT Bio FROM Bio WHERE Bio.account.id = ?1")
    Optional<Bio> findBioById(Long id);

    @Query("DELETE FROM Bio WHERE Bio.account.id = ?1")
    void deleteBioById(Long id);
}
