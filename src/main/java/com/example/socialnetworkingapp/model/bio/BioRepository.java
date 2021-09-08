package com.example.socialnetworkingapp.model.bio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface BioRepository extends JpaRepository<Bio, Long> {

    @Query("SELECT b FROM Bio b WHERE b.account.id = ?1")
    Optional<Bio> findBioByAccountId(Long id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Bio b WHERE b.account.id = ?1")
    void deleteBioByAccountId(Long id);
}
