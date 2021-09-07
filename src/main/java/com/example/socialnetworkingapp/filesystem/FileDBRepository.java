package com.example.socialnetworkingapp.filesystem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FileDBRepository extends JpaRepository<FileDB, String> {

    @Query("SELECT f FROM FileDB f WHERE f.ownerEmail = ?1")
    FileDB findIdByOwnerEmail(String email);
}
