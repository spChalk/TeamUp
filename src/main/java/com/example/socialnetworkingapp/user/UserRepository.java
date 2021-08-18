package com.example.socialnetworkingapp.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Usr, Long> {

    @Query("SELECT u FROM Usr u WHERE u.email = ?1")
    Optional<Usr> findUserByEmail(String email);

}
