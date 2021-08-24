package com.example.socialnetworkingapp.account;

import com.example.socialnetworkingapp.exception.UserAlreadyRegisteredException;
import com.example.socialnetworkingapp.exception.UserNotFoundException;
import com.example.socialnetworkingapp.account.Account;
import com.example.socialnetworkingapp.account.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import java.util.function.*;
import java.util.OptionalInt;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountService implements UserDetailsService {
    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public String AccountSignUp(Account account){
        boolean present = accountRepository.findAccountByEmail(account.getUsername()).isPresent();

        if(present){
            throw new UserAlreadyRegisteredException("User with same email : " + account.getUsername() + " already exists!");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(account.getPassword());

        account.setPassword(encodedPassword);

        accountRepository.save(account);
        return "workeeeed ";
    }

        public Account addAccount(Account account){
        return accountRepository.save(account);
    }

    public List<Account> findAllAccounts(){
        return accountRepository.findAll();
    }

    public Account updateAccount(Account account){
        return accountRepository.save(account);
    }

    public void deleteAccount(Long id){
        accountRepository.deleteAccountById(id);
    }

    public Account findAccountById(Long id){
        return accountRepository.findAccountById(id).orElseThrow( () -> new UserNotFoundException("User by id "+ id + "was not found !"));
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return accountRepository.findAccountByEmail(email).orElseThrow( () -> new UserNotFoundException("User with email: " + email + " was not found"));
    }
}
