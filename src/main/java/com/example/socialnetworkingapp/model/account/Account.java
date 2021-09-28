package com.example.socialnetworkingapp.model.account;

import com.example.socialnetworkingapp.model.bio.Bio;
import com.example.socialnetworkingapp.model.education.Education;
import com.example.socialnetworkingapp.model.experience.Experience;
import com.example.socialnetworkingapp.model.tags.Tag;
import com.example.socialnetworkingapp.security.jwt.CustomAuthorityDeserializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.*;

@Data
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="accounts")
public class Account implements UserDetails {

    @Id
    @TableGenerator(name = "Id_Gen", initialValue = 101)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "Id_Gen")
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AccountRole role;

    private String firstName;
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String phone;
    private String imageUrl;

    @NotNull
    private LocalDate dateCreated = LocalDate.now();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "network",
            joinColumns = { @JoinColumn(name = "account1_id") },
            inverseJoinColumns = { @JoinColumn(name = "account2_id") })
    @JsonSerialize(using = CustomNetworkSerializer.class)
    private List<Account> network = new ArrayList<Account>();

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.MERGE
            })
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(name = "account_tags",
            joinColumns = { @JoinColumn(name = "account_id") },
            inverseJoinColumns = { @JoinColumn(name = "tag_id") })
    private List<Tag> tags = new ArrayList<>();

    @NotNull
    private boolean visibleTags = true;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.REMOVE,
                    CascadeType.MERGE
            })
    @JoinTable(name = "account_experience",
            joinColumns = { @JoinColumn(name = "account_id") },
            inverseJoinColumns = { @JoinColumn(name = "experience_id") })
    private List<Experience> experience = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.REMOVE,
                    CascadeType.MERGE
            })
    @JoinTable(name = "account_education",
            joinColumns = { @JoinColumn(name = "account_id") },
            inverseJoinColumns = { @JoinColumn(name = "education_id") })
    private List<Education> education = new ArrayList<>();

    @OneToOne(fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.REMOVE,
                    CascadeType.MERGE
            })
    @JoinTable(name = "account_bio",
            joinColumns = { @JoinColumn(name = "account_id") },
            inverseJoinColumns = { @JoinColumn(name = "bio_id") })
    private Bio bio;

    public Account(AccountRole role, String firstName, String lastName, String email, String password, String phone) {
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public Account(AccountRole role, String firstName, String lastName, String email, String password, String phone, String url) {
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.imageUrl = url;
    }

    @JsonDeserialize(using = CustomAuthorityDeserializer.class)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role.name());
        return Collections.singletonList(simpleGrantedAuthority);

    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }


    public String getFirstName(){ return firstName;}

    public String getLastName(){return lastName; }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    public Long getId(){
        return this.id;
    }
}


