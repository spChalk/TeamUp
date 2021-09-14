package com.example.socialnetworkingapp.model.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface AccountRepository extends JpaRepository<Account, Long> {
    void deleteAccountById(Long id);
    Optional<Account> findAccountById(Long id);
    Optional<Account> findAccountByEmail(String email);
    Optional<Account> findAccountByPhone(String phone);

    @Query("SELECT a FROM Account a WHERE a.email LIKE %?1%")
    Optional<List<Account>> findAccountBySimilarEmail(String email_fragment);

    @Query("SELECT a FROM Account a WHERE ( a.firstName LIKE %?1% ) OR ( a.lastName LIKE %?1% )")
    Optional<List<Account>> findAccountBySimilarName(String keyword);
}
