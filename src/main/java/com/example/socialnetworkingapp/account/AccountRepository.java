package com.example.socialnetworkingapp.account;

import com.example.socialnetworkingapp.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface AccountRepository extends JpaRepository<Account, Long> {
    void deleteAccountById(Long id);
    Optional<Account> findAccountByEmail(String email);
    Optional<Account> findAccountById(Long id);
}
