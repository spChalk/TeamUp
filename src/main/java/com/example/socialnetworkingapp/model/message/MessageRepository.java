package com.example.socialnetworkingapp.model.message;

import com.example.socialnetworkingapp.model.account.Account;
import com.example.socialnetworkingapp.model.bio.Bio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@Transactional
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m WHERE m.sender = ?1 AND m.receiver = ?2 OR m.sender = ?2 AND m.receiver = ?1")
    Optional<List<Message>> getConversation(Account sender_id, Account receiver_id);

    @Query("SELECT m.receiver FROM Message m WHERE m.sender = ?1")
    Optional<Set<Account>> getSentMessages(Account user);

    @Query("SELECT m.sender FROM Message m WHERE m.receiver = ?1")
    Optional<Set<Account>> getReceivedMessages(Account user);
}
