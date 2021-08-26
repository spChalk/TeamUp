package com.example.socialnetworkingapp.model.bio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface BioRepository extends JpaRepository<Bio, Long> {

    @Query("SELECT b FROM Bio b WHERE b.account.id = ?1")
    Optional<Bio> findBioById(Long id);

    @Query("DELETE FROM Bio WHERE account.id = ?1")
    void deleteBioById(Long id);
}
