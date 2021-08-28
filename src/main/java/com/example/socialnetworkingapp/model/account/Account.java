package com.example.socialnetworkingapp.model.account;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
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
    private AppUserRole appUserRole;

    private String firstName;
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String phone;
    private String imageUrl;

    /*
     * Επειδη υπαρχουν followers, following αλλά και connections στο linkedin, θα κανουμε το εξης:
     * - Aν ακολουθω εναν χρηστη ο οποιος με ακολουθει και εκεινος, τοτε σχηματιζουμε ενα connection.
    */

    //friends , many-to-many self referencing
    @ManyToMany(mappedBy = "following", cascade = CascadeType.ALL)
    private List<Account> followers = new ArrayList<Account>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="user_connections",
            joinColumns={@JoinColumn(name="FriendId")},
            inverseJoinColumns={@JoinColumn(name="AccountId")})
    private List<Account> following = new ArrayList<Account>();


    public Account(AppUserRole appUserRole, String firstName, String lastName, String email, String password, String phone) {
        this.appUserRole = appUserRole;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(appUserRole.name());
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
        if(friend != null) {
            friend.getFollowers().add(this);
        }
    }
}


