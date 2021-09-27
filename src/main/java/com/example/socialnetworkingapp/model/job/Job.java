package com.example.socialnetworkingapp.model.job;

import com.example.socialnetworkingapp.model.account.Account;
import com.example.socialnetworkingapp.model.tags.Tag;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "jobs")
public class Job implements Serializable {

		@Id
		@TableGenerator(name = "Id_Gen", initialValue = 101)
		@GeneratedValue(strategy = GenerationType.TABLE, generator = "Id_Gen")
		@Column(nullable = false, updatable = false, unique = true)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Account publisher;

    @NotNull
    private String location;

    private Date date;

    @NotNull
    private JobType jobType;

    @NotNull
    private ExperienceLevel experienceLevel;

    @NotNull
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String info;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.MERGE
            })
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(name = "job_tags",
            joinColumns = { @JoinColumn(name = "job_id") },
            inverseJoinColumns = { @JoinColumn(name = "tag_id") })
    private List<Tag> tags = new ArrayList<>();

    public Job(String title, Account publisher, String location, Date date, JobType jobType, ExperienceLevel experienceLevel, String info, List<Tag> tags) {
        this.title = title;
        this.publisher = publisher;
        this.location = location;
        this.date = date;
        this.jobType = jobType;
        this.experienceLevel = experienceLevel;
        this.info = info;
        this.tags = tags;
    }
}