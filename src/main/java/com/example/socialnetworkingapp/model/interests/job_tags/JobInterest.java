package com.example.socialnetworkingapp.model.interests.job_tags;

import com.example.socialnetworkingapp.model.account.Account;
import com.example.socialnetworkingapp.model.interests.Tags;
import com.example.socialnetworkingapp.model.job.Job;
import com.sun.istack.NotNull;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "job_tags")
public class JobInterest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;

    @NotNull
    @ManyToOne
    private Job job;

    @NotNull
    private Tags tag;
}