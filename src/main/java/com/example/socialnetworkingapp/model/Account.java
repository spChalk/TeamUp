package com.example.socialnetworkingapp.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@Entity
@Table(name="accounts")
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;


    private String firstName;
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;


    @Column(unique = true)
    private String phone;
    private String imageUrl;

    @ManyToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<AccountSetting> accountSettings = new ArrayList<AccountSetting>();

//    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
//    private List<Message> messages = new ArrayList<Message>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Article> articles = new ArrayList<Article>();

//    @ManyToMany(mappedBy = "friend", cascade = CascadeType.ALL)
//    private List<Account> friends = new ArrayList<Account>();

    @ManyToMany(mappedBy = "advertiser", cascade = CascadeType.ALL)
    private List<Advertisment> advertisments = new ArrayList<Advertisment>();

}
