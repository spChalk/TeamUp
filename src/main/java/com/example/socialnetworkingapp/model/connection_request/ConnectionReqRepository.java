package com.example.socialnetworkingapp.model.connection_request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ConnectionReqRepository extends JpaRepository<ConnectionRequest, Long> {

    @Query("SELECT ConnectionRequest FROM ConnectionRequest WHERE ConnectionRequest.receiver.id = ?1")
    Optional<List<ConnectionRequest>> findRequestsByAccId(Long id);
}
