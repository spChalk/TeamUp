package com.example.socialnetworkingapp.model.account;

import com.example.socialnetworkingapp.exception.UserAlreadyRegisteredException;
import com.example.socialnetworkingapp.exception.UserNotFoundException;
import com.example.socialnetworkingapp.mapper.AccountMapper;
import com.example.socialnetworkingapp.model.connection_request.ConnectionRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccountService implements UserDetailsService {
    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AccountMapper accountMapper;

    /*
    *   In Account, 3 fields are unique:
    *   1. Id (Generated automatically)
    *   2. Email
    *   3. Phone
    *
    * In order for us to NOT get an Internal SLQ error, we have to check for duplicates.
    */
    public Optional<Account> checkExistence(String fieldName, Optional<Account> opt) {

        if(opt.isPresent()) {
            throw new UserAlreadyRegisteredException("User with the same " + fieldName + " already exists!");
        }
        return opt;
    }

    public Account checkAccount(Account account) {

        checkExistence("email", this.accountRepository.findAccountByEmail(account.getEmail()));
        checkExistence("phone", this.accountRepository.findAccountByPhone(account.getPhone()));

        String encodedPassword = bCryptPasswordEncoder.encode(account.getPassword());
        account.setPassword(encodedPassword);

        return account;
    }

    public String accountSignUp(Account account){

        this.accountRepository.save(checkAccount(account));

        return "Account registered successfully!";
    }

    public Account addAccount(Account account){

        return this.accountRepository.save(checkAccount(account));
    }

    public List<Account> findAllAccounts(){

        return this.accountRepository.findAll();
        /*return accountRepository.findAll().stream().map(accountMapper::AccountToAccountResponse).collect(Collectors.toList());*/
    }

    public Account updateAccount(Account account){

        Optional<Account> accPresent = this.accountRepository.findAccountById(account.getId());
        if(!accPresent.isPresent()) {
            throw new UserNotFoundException("User with id " + account.getId() + " does not exist!");
        }

        Account acc = accPresent.get();
        acc.setFirstName(account.getFirstName());
        acc.setLastName(account.getLastName());
        acc.setEmail(account.getEmail());
        acc.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
        acc.setPhone(account.getPhone());
        acc.setImageUrl(account.getImageUrl());
        return this.accountRepository.save(acc);
    }

    public void deleteAccount(Long id){
        accountRepository.deleteAccountById(id);
    }

    public Account findAccountById(Long id){
        return accountRepository.findAccountById(id).orElseThrow( () -> new UserNotFoundException("User with id "+ id + " was not found!"));
    }

    public Account findAccountByEmail(String email){
        return accountRepository.findAccountByEmail(email).orElseThrow( () -> new UserNotFoundException("User with email "+ email + " was not found!"));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return accountRepository.findAccountByEmail(email).orElseThrow( () -> new UserNotFoundException("User with email " + email + " was not found!"));
    }

    public void follow(Account sender, Account receiver) {
        /*
        * If receiver is not in my follows, add him
        * Also, add me in receiver's followers
        */
        if(sender.getFollowing().isEmpty() || sender.getFollowing().stream()
                .filter(contact -> receiver.getEmail().equals(contact.getEmail()))
                .findAny()
                .orElse(null) == null) {
            sender.follow(receiver);
        }
    }

    public void createAdmin() {

        Account account = new Account(AccountRole.ADMIN, "admin", "admin", "admin@admin.com", "admin", "12345");
        String encodedPassword = bCryptPasswordEncoder.encode(account.getPassword());
        account.setPassword(encodedPassword);
        this.accountRepository.save(account);
    }
}
