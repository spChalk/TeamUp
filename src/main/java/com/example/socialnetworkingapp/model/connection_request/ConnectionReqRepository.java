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

    @Query("SELECT cr FROM ConnectionRequest cr WHERE cr.receiver.id = ?1 AND cr.requestStatus = 0")
    Optional<List<ConnectionRequest>> findPendingRequestsByAccId(Long id);

    @Query("SELECT cr FROM ConnectionRequest cr WHERE cr.sender.id = ?1 AND cr.receiver.id = ?2 AND cr.requestStatus = 0")
    Optional<ConnectionRequest> findPendingRequestByAccIds(Long sender, Long receiver);

    @Query("SELECT cr FROM ConnectionRequest cr WHERE cr.receiver.id = ?1 AND cr.requestStatus = 1")
    Optional<List<ConnectionRequest>> findReceivedAcceptedRequestsByAccId(Long id);

    @Query("SELECT cr FROM ConnectionRequest cr WHERE cr.sender.id = ?1 AND cr.requestStatus = 1")
    Optional<List<ConnectionRequest>> findSentAcceptedRequestsByAccId(Long id);

    @Modifying
    @Transactional
    @Query("UPDATE ConnectionRequest cr SET cr.requestStatus = 1 WHERE cr.id = ?1")
    void acceptRequest(Long id);

    @Modifying
    @Transactional
    @Query("UPDATE ConnectionRequest cr SET cr.requestStatus = 2 WHERE cr.id = ?1")
    void rejectRequest(Long id);

    @Modifying
    @Transactional
    @Query("DELETE FROM ConnectionRequest cr WHERE (cr.sender.id = ?1 AND cr.receiver.id = ?2)" +
            "OR (cr.sender.id = ?2 AND cr.receiver.id = ?1)")
    void deleteByAccIds(Long me, Long uid);
}
