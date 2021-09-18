package com.example.socialnetworkingapp.model.connection_request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ConnectionReqRepository extends JpaRepository<ConnectionRequest, Long> {

    @Query("SELECT cr FROM ConnectionRequest cr WHERE cr.receiver.id = ?1")
    Optional<List<ConnectionRequest>> findRequestsByAccId(Long id);

    @Query("SELECT cr FROM ConnectionRequest cr WHERE cr.sender.id = ?1 AND cr.receiver.id = ?2")
    Optional<ConnectionRequest> findRequestByAccIds(Long sender, Long receiver);

/*
    @Query("SELECT cr FROM ConnectionRequest cr WHERE (cr.sender.email = ?1 AND cr.receiver.email = ?2) OR " +
            "(cr.sender.email = ?2 AND cr.receiver.email = ?1) AND cr.requestStatus = 1")
    ConnectionRequest findAcceptedRequestsByEmails(String email1, String email2);*/

 /*   @Query("SELECT cr FROM ConnectionRequest cr WHERE cr.receiver.id = ?1 AND cr.requestStatus = 1")
    Optional<List<ConnectionRequest>> findReceivedAcceptedRequestsByAccId(Long id);

    @Query("SELECT cr FROM ConnectionRequest cr WHERE cr.sender.id = ?1 AND cr.requestStatus = 1")
    Optional<List<ConnectionRequest>> findSentAcceptedRequestsByAccId(Long id);

    @Query("SELECT cr FROM ConnectionRequest cr WHERE cr.receiver.email = ?1 AND cr.requestStatus = 1")
    Optional<List<ConnectionRequest>> findReceivedAcceptedRequestsByAccEmail(String email);

    @Query("SELECT cr FROM ConnectionRequest cr WHERE cr.sender.email = ?1 AND cr.requestStatus = 1")
    Optional<List<ConnectionRequest>> findSentAcceptedRequestsByAccEmail(String email);*/

    @Modifying
    @Transactional
    @Query("DELETE FROM ConnectionRequest cr WHERE (cr.sender.id = ?1 AND cr.receiver.id = ?2)" +
            "OR (cr.sender.id = ?2 AND cr.receiver.id = ?1)")
    void deleteByAccIds(Long me, Long uid);
}
