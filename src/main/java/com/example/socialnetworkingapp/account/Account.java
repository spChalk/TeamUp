package com.example.socialnetworkingapp.account;


import com.example.socialnetworkingapp.post.Post;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.*;

@Data
@Getter
@Setter
@EqualsAndHashCode
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

    public Account(AppUserRole appUserRole, String firstName, String lastName, String email, String password, String phone) {
        this.appUserRole = appUserRole;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }


    //posts by a user
    // a user can write many posts

//    @ManyToMany(mappedBy = "user",cascade = CascadeType.ALL)
//    private List<AccountSetting> accountSettings = new ArrayList<AccountSetting>();

//    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
//    private List<Message> messages = new ArrayList<Message>();

    //    @ManyToMany(mappedBy = "friend", cascade = CascadeType.ALL)
//    private List<Account> friends = new ArrayList<Account>();

//    @ManyToMany(mappedBy = "advertiser", cascade = CascadeType.ALL)

//    private List<Advertisment> advertisments = new ArrayList<Advertisment>();



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
}
