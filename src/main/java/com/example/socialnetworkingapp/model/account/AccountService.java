package com.example.socialnetworkingapp.model.account;

import com.example.socialnetworkingapp.exception.UserAlreadyRegisteredException;
import com.example.socialnetworkingapp.exception.UserNotFoundException;
import com.example.socialnetworkingapp.mapper.AccountMapper;
import com.example.socialnetworkingapp.model.account.details.*;
import com.example.socialnetworkingapp.model.bio.Bio;
import com.example.socialnetworkingapp.model.bio.BioService;
import com.example.socialnetworkingapp.model.comment.Comment;
import com.example.socialnetworkingapp.model.comment.CommentRequest;
import com.example.socialnetworkingapp.model.comment.CommentResponse;
import com.example.socialnetworkingapp.model.comment.CommentService;
import com.example.socialnetworkingapp.model.education.Education;
import com.example.socialnetworkingapp.model.education.EducationService;
import com.example.socialnetworkingapp.model.experience.Experience;
import com.example.socialnetworkingapp.model.experience.ExperienceService;
import com.example.socialnetworkingapp.model.job.Job;
import com.example.socialnetworkingapp.model.job.JobResponse;
import com.example.socialnetworkingapp.model.job.JobService;
import com.example.socialnetworkingapp.model.like.Like;
import com.example.socialnetworkingapp.model.like.LikeResponse;
import com.example.socialnetworkingapp.model.like.LikeService;
import com.example.socialnetworkingapp.model.post.Post;
import com.example.socialnetworkingapp.model.post.PostResponse;
import com.example.socialnetworkingapp.model.post.PostService;
import com.example.socialnetworkingapp.model.tags.Tag;
import com.example.socialnetworkingapp.model.tags.TagService;
import com.example.socialnetworkingapp.registration.RegistrationRequest;
import lombok.AllArgsConstructor;
import org.assertj.core.api.OptionalAssert;
import org.springframework.boot.autoconfigure.batch.JobExecutionEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
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
    private final PostService postService;
    private final JobService jobService;
    private final LikeService likeService;
    private final CommentService commentService;
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

    public List<Account> findAllAccounts() {
        return this.accountRepository.findAll();
    }

    public List<FullAccountDetails> getFullAccountsDetails(List<Long> accountIds) throws IOException {
        List<FullAccountDetails> details = new ArrayList<>();
        for(Long id: accountIds) {
            /* Account's personal details */
            Account tempAccount = this.findAccountById(id);
            /* Account's network */
            List<AccountDetails> network = new ArrayList<>();
            for (Account friend : tempAccount.getNetwork()) {
                network.add(
                        new AccountDetails(
                                friend.getFirstName(),
                                friend.getLastName(),
                                friend.getEmail(),
                                friend.getPhone(),
                                friend.getImageUrl(),
                                friend.getDateCreated().toString()
                        )
                );
            }
            /* Account's tags */
            List<String> tags = new ArrayList<>();
            for (Tag tag : tempAccount.getTags()) {
                tags.add(tag.getTag());
            }
            /* Account's experience */
            List<ExperienceDetails> experience = new ArrayList<>();
            for (Experience xp : tempAccount.getExperience()) {
                experience.add(
                        new ExperienceDetails(
                                xp.getTitle(),
                                String.valueOf(xp.getEmploymentType()),
                                xp.getCompany(),
                                xp.getLocation(),
                                xp.getStartDate(),
                                xp.getEndDate(),
                                xp.getHeadline(),
                                xp.getDescription()
                        )
                );
            }
            /* Account's education */
            List<EducationDetails> education = new ArrayList<>();
            for (Education edu : tempAccount.getEducation()) {
                education.add(
                        new EducationDetails(
                                edu.getSchool(),
                                edu.getDegree(),
                                edu.getField(),
                                edu.getStartDate(),
                                edu.getEndDate(),
                                edu.getGrade(),
                                edu.getDescription()
                        )
                );
            }
            /* Account's posts */
            List<PostDetails> posts = new ArrayList<>();
            for (PostResponse post : this.postService.findPostsByAuthorId(tempAccount.getId())) {
                posts.add(
                        new PostDetails(
                                post.getPayload(),
                                post.getDate(),
                                post.getImagePath(),
                                post.getVideoPath(),
                                post.getSoundPath()
                        )
                );
            }
            /* Account's jobs */
            List<JobDetails> jobs = new ArrayList<>();
            for (Job job : this.jobService.getJobsUnsorted(tempAccount.getId())) {

                List<String> jobTags = new ArrayList<>();
                for (Tag tag : job.getTags()) {
                    jobTags.add(tag.getTag());
                }

                jobs.add(
                        new JobDetails(
                                job.getTitle(),
                                job.getLocation(),
                                job.getDate().toString(),
                                job.getJobType().toString(),
                                job.getExperienceLevel().toString(),
                                job.getInfo(),
                                jobTags
                        )
                );
            }
            /* Account's likes */
            List<LikeDetails> likes = new ArrayList<>();
            for (Like like : this.likeService.findLikesByUserId(tempAccount.getId())) {
                likes.add(
                        new LikeDetails(
                                like.getDateCreated().toString(),
                                like.getPost().getAuthor().getFirstName(),
                                like.getPost().getAuthor().getLastName(),
                                like.getPost().getAuthor().getEmail(),
                                like.getPost().getPayload(),
                                like.getPost().getDate().toString(),
                                like.getPost().getImagePath(),
                                like.getPost().getVideoPath(),
                                like.getPost().getSoundPath()
                        )
                );
            }
            /* Account's comments */
            List<CommentDetails> comments = new ArrayList<>();
            for (Comment comment: this.commentService.findCommentsByUserId(tempAccount.getId())) {
                comments.add(
                        new CommentDetails(
                                comment.getPayload(),
                                comment.getDate(),
                                comment.getPost().getAuthor().getFirstName(),
                                comment.getPost().getAuthor().getLastName(),
                                comment.getPost().getAuthor().getEmail(),
                                comment.getPost().getPayload(),
                                comment.getPost().getDate().toString(),
                                comment.getPost().getImagePath(),
                                comment.getPost().getVideoPath(),
                                comment.getPost().getSoundPath()

                        )
                );
            }

            details.add(
                    new FullAccountDetails(
                            tempAccount.getFirstName(),
                            tempAccount.getLastName(),
                            tempAccount.getEmail(),
                            tempAccount.getPhone(),
                            tempAccount.getImageUrl(),
                            tempAccount.getDateCreated().toString(),
                            network,
                            tags,
                            experience,
                            education,
                            tempAccount.getBio() != null ? tempAccount.getBio().getDescription() : "No bio available",
                            posts,
                            jobs,
                            likes,
                            comments
                            )
            );
        }
        return details;
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

    public Education updateEducation(String email, Education education) {
        Account account = findAccountByEmail(email);

        Optional<Education> previousEducation = this.educationService.findEducationById(education.getId());

        if(!previousEducation.isPresent()) {
            throw new UserNotFoundException("Could find education with id " + education.getId() + " while trying to update it!");
        }

        Education prevEducation = previousEducation.get();

        if(!education.getSchool().equals(""))
            prevEducation.setSchool(education.getSchool());

        if(!education.getDegree().equals(""))
            prevEducation.setDegree(education.getDegree());

        if(!education.getField().equals(""))
            prevEducation.setField(education.getField());

        if(!education.getStartDate().equals(""))
            prevEducation.setStartDate(education.getStartDate());

        if(!education.getEndDate().equals(""))
            prevEducation.setEndDate(education.getEndDate());

        if(!String.valueOf(education.getGrade()).equals(""))
            prevEducation.setGrade(education.getGrade());

        if(!education.getDescription().equals(""))
            prevEducation.setDescription(education.getDescription());

        Education newEducation = this.educationService.updateEducation(prevEducation);
        this.accountRepository.save(account);
        return newEducation;
    }

    public Experience updateExperience(String email, Experience experience) {
        Account account = findAccountByEmail(email);
        Optional<Experience> previousExperience = this.experienceService.findExperienceById(experience.getId());

        if(!previousExperience.isPresent()) {
            throw new UserNotFoundException("Could find experience with id " + experience.getId() + " while trying to update it!");
        }

        Experience prevExperience = previousExperience.get();

        if(!experience.getTitle().equals(""))
            prevExperience.setTitle(experience.getTitle());

        if(!String.valueOf(experience.getEmploymentType()).equals(""))
            prevExperience.setEmploymentType(experience.getEmploymentType());

        if(!String.valueOf(experience.getExperienceLevel()).equals(""))
            prevExperience.setExperienceLevel(experience.getExperienceLevel());

        if(!experience.getCompany().equals(""))
            prevExperience.setCompany(experience.getCompany());

        if(!experience.getLocation().equals(""))
            prevExperience.setLocation(experience.getLocation());

        if(!experience.getStartDate().equals(""))
            prevExperience.setStartDate(experience.getStartDate());

        if(!experience.getEndDate().equals(""))
            prevExperience.setEndDate(experience.getEndDate());

        if(!experience.getHeadline().equals(""))
            prevExperience.setHeadline(experience.getHeadline());

        if(!experience.getDescription().equals(""))
            prevExperience.setDescription(experience.getDescription());


        Experience newExperience = this.experienceService.updateExperience(prevExperience);
        this.accountRepository.save(account);
        return newExperience;
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
