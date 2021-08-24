//package com.example.socialnetworkingapp.model;
//
//import lombok.Data;
//
//import javax.persistence.*;
//import java.io.Serializable;
//
//@Data
//@Entity
//@Table(name = "account_settings")
//public class AccountSetting implements Serializable {
//
//    @Id
//    @GeneratedValue
//    private Long id;
//
//    @Column(name = "name", nullable = false)
//    private String settingName;
//
//    @Column(name = "value", nullable = false)
//    private String settingValue;
//
//    @ManyToOne
//    @JoinColumn(name ="account_id", nullable = false)
//    private Account user;
//
//    // getters and setters
//}
