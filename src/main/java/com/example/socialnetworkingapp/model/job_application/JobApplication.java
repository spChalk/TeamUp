package com.example.socialnetworkingapp.model.job_application;

import com.example.socialnetworkingapp.account.Account;
import com.example.socialnetworkingapp.model.job.Job;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "job_application")
@AllArgsConstructor
@NoArgsConstructor
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id ;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name ="account_id", nullable = false)
    private Account user;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name ="job_id", nullable = false)
    private Job job;
}