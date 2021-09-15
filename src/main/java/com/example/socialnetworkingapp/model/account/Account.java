package com.example.socialnetworkingapp.model.account;

import com.example.socialnetworkingapp.model.tags.Tag;
import com.example.socialnetworkingapp.security.jwt.CustomAuthorityDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sun.istack.NotNull;
import lombok.*;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    //friends , many-to-many self referencing
    @ManyToMany(mappedBy = "following", cascade = CascadeType.ALL)
    private List<Account> followers = new ArrayList<Account>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="user_connections",
            joinColumns={@JoinColumn(name="user1_id")},
            inverseJoinColumns={@JoinColumn(name="user2_id")})
    private List<Account> following = new ArrayList<Account>();

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "account_tags",
            joinColumns = { @JoinColumn(name = "account_id") },
            inverseJoinColumns = { @JoinColumn(name = "tag_id") })
    private List<Tag> tags = new ArrayList<>();

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

    public void follow(Account friend){
        this.following.add(friend);
        friend.getFollowers().add(this);
    }
}


