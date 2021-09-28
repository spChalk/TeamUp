package com.example.socialnetworkingapp.model.bio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface BioRepository extends JpaRepository<Bio, Long> {

}
