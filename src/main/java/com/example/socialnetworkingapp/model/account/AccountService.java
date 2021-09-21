package com.example.socialnetworkingapp.model.account;

import com.example.socialnetworkingapp.exception.UserAlreadyRegisteredException;
import com.example.socialnetworkingapp.exception.UserNotFoundException;
import com.example.socialnetworkingapp.mapper.AccountMapper;
import com.example.socialnetworkingapp.model.bio.Bio;
import com.example.socialnetworkingapp.model.bio.BioService;
import com.example.socialnetworkingapp.model.education.Education;
import com.example.socialnetworkingapp.model.education.EducationService;
import com.example.socialnetworkingapp.model.experience.Experience;
import com.example.socialnetworkingapp.model.experience.ExperienceService;
import com.example.socialnetworkingapp.model.tags.Tag;
import com.example.socialnetworkingapp.model.tags.TagService;
import com.example.socialnetworkingapp.registration.RegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountService implements UserDetailsService {
    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TagService tagService;
    private final ExperienceService experienceService;
    private final EducationService educationService;
    private final BioService bioService;
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

    public Account accountSignUp(RegistrationRequest request){

        Account account = new Account(AccountRole.USER, request.getFirstName(), request.getLastName(), request.getEmail(), request.getPassword(), request.getPhone());
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
        acc.setBio(account.getBio());
        acc.setVisibleTags(account.isVisibleTags());
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

    public List<Account> findAccountsBySimilarEmail(String keyword) {
        return accountRepository.findAccountBySimilarEmail(keyword).get();
    }

    public List<Account> findAccountsBySimilarName(String keyword) {
        return accountRepository.findAccountBySimilarName(keyword).get();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return accountRepository.findAccountByEmail(email).orElseThrow( () -> new UserNotFoundException("User with email " + email + " was not found!"));
    }

   /* public void follow(Account sender, Account receiver) {
        *//*
        * If receiver is not in my follows, add him
        * Also, add me in receiver's followers
        *//*
        if(sender.getFollowing().isEmpty() || sender.getFollowing().stream()
                .filter(contact -> receiver.getEmail().equals(contact.getEmail()))
                .findAny()
                .orElse(null) == null) {
            sender.follow(receiver);
        }
    }*/

    public void createAdmin() {

        Account account = new Account(AccountRole.ADMIN, "admin", "admin", "admin@admin.com", "adminadmin", "12345");
        String encodedPassword = bCryptPasswordEncoder.encode(account.getPassword());
        account.setPassword(encodedPassword);
        this.accountRepository.save(account);
    }

    public Account addTag(String tagName, String email) {

        Account account = findAccountByEmail(email);
        Tag existingTag = this.tagService.getTagByName(tagName);
        if(existingTag == null) {
            account.getTags().add(this.tagService.addTag(new Tag(tagName)));
        } else {
            account.getTags().add(existingTag);
        }
        return this.accountRepository.save(account);
    }

    public List<Tag> getUserTags() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Account account = this.findAccountByEmail(email);
        return account.getTags();
    }

    public Account addExperience(String email, Experience xp) {

        Account account = findAccountByEmail(email);
        account.getExperience().add(this.experienceService.addExperience(xp));
        return this.accountRepository.save(account);
    }

    public List<Experience> getExperience(String email) {

        Account account = this.accountRepository.findAccountByEmail(email).orElseThrow(
                () -> new UserNotFoundException("User with email " + email + " was not found!")
        );
        return account.getExperience();
    }

    public List<Education> getEducation(String email) {
        Account account = this.accountRepository.findAccountByEmail(email).orElseThrow(
                () -> new UserNotFoundException("User with email " + email + " was not found!")
        );
        return account.getEducation();
    }

    public Bio addBio(String email, Bio bio) {
        Account account = findAccountByEmail(email);
        account.setBio(this.bioService.addBio(bio));
        this.accountRepository.save(account);
        return account.getBio();
    }

    public Account addEducation(String email, Education education) {
        Account account = findAccountByEmail(email);
        account.getEducation().add(this.educationService.addEducation(education));
        return this.accountRepository.save(account);
    }

    public void deleteBio(Long uid) {
        Account account = this.accountRepository.findAccountById(uid).orElseThrow(
                () -> new UserNotFoundException("User with id " + uid.toString() + " does not exist!")
        );
        account.setBio(null);
        this.accountRepository.save(account);
    }

    public Account hideTags(Account acc) {
        Account account = this.accountRepository.findAccountById(acc.getId()).orElseThrow(
                () -> new UserNotFoundException("User with id " + acc.getId().toString() + " does not exist!")
        );
        account.setVisibleTags(false);
        return this.accountRepository.save(account);
    }

    public Account showTags(Account acc) {
        Account account = this.accountRepository.findAccountById(acc.getId()).orElseThrow(
                () -> new UserNotFoundException("User with id " + acc.getId().toString() + " does not exist!")
        );
        account.setVisibleTags(true);
        return this.accountRepository.save(account);
    }

    public void connect(String senderEmail, String receiverEmail) {
        Account sender = findAccountByEmail(senderEmail);
        Account receiver = findAccountByEmail(receiverEmail);
        sender.getNetwork().add(receiver);
        receiver.getNetwork().add(sender);
        this.accountRepository.save(sender);
        this.accountRepository.save(receiver);
    }

    public void removeConnection(String user1Email, String user2Email) {
        Account usr1 = findAccountByEmail(user1Email);
        Account usr2 = findAccountByEmail(user2Email);
        usr1.getNetwork().remove(usr2);
        usr2.getNetwork().remove(usr1);
        this.accountRepository.save(usr1);
        this.accountRepository.save(usr2);
    }

    public Account aboutUpdateAccount(AccountUpdateRequest account) {

        Optional<Account> accPresent = this.accountRepository.findAccountByEmail(account.getEmail());
        if(!accPresent.isPresent()) {
            throw new UserNotFoundException("User with email " + account.getEmail() + " does not exist!");
        }

        Account acc = accPresent.get();

        if(!account.getFirstName().equals(""))
        acc.setFirstName(account.getFirstName());
        if(!account.getLastName().equals(""))
        acc.setLastName(account.getLastName());
        if(!account.getPhone().equals(""))
        acc.setPhone(account.getPhone());
        return this.accountRepository.save(acc);
    }
}
