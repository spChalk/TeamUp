package com.example.socialnetworkingapp.model.job;

import com.example.socialnetworkingapp.mapper.JobMapper;
import com.example.socialnetworkingapp.model.account.Account;
import com.example.socialnetworkingapp.model.account.AccountRepository;
import com.example.socialnetworkingapp.model.tags.Tag;
import com.example.socialnetworkingapp.model.job_view.JobViewRepository;
import com.example.socialnetworkingapp.model.tags.TagService;
import lombok.AllArgsConstructor;
import org.checkerframework.checker.nullness.Opt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class JobService {

    private final JobRepository jobRepository;
    private final AccountRepository accountRepository;
    private final JobViewRepository jobViewRepository;
    private final TagService tagService;
    private final JobMapper jobMapper;
    /*
    private final AccountInterestsService accountInterestsService;
    private final JobInterestsRepository jobInterestsRepository;
*/

    public Job findJobById(Long id) {
        return this.jobRepository.findById(id).
                orElseThrow(() -> new IllegalStateException("Job with id " + id + " was not found!" ));
    }

/*    private List<Job> tagFilter(ArrayList<Pair<Long, Long>> arrayOfTuples, List<AccountInterest> userAccountTags,
                                Job[] allJobs, HashMap<Long, Integer> jobsMap) {
        // 13. TAG FILTERING
        *//*
         *   14. For every Job with id === tuple.job-id, see how many tags the job and the user have in common
         *           and for every tag, add 1 to views.
         *//*
        for(int i = 0; i < arrayOfTuples.size(); i++) {
            List<JobInterest> jobTags = this.jobInterestsRepository.findAllByJobId(arrayOfTuples.get(i).getValue1());
            for(JobInterest tag: jobTags) {
                for(AccountInterest accTag: userAccountTags) {
                    if(tag.getTag().equals(accTag.getTag())) {
                        arrayOfTuples.set(i, arrayOfTuples.get(i).setAt0(arrayOfTuples.get(i).getValue0() + 1));
                    }
                }
            }
        }
        Comparator<Pair<Long, Long>> comparator = new Comparator<Pair<Long, Long>>() {
            public int compare(Pair<Long, Long> tupleA,
                               Pair<Long, Long> tupleB) {
                return tupleA.getValue0().compareTo(tupleB.getValue0());
            }
        };
        // 15. SORT the array of tuples, by views.
        Collections.sort(arrayOfTuples, comparator);

        // 16. Make an empty list, iterate the tuple array, and push() all the Job classes s.t Job.id == tuple.job-id
        List<Job> result = new ArrayList<>();
        for(Pair<Long, Long> tuple: arrayOfTuples) {
            result.add(allJobs[jobsMap.get(tuple.getValue1())]);
        }
        // 17. Return the list of jobs.
        return result;
    }*/

    public void printJob(Job job) {
        System.out.println("---JOB---");
        System.out.println(job.getId());
        System.out.println("---------");
    }

/*    public void printAccount(Account account) {
        System.out.println("---ACCOUNT---");
        System.out.println(account.getId());
        System.out.println(account.getLastName());
        System.out.println("-------------");
    }

    public void printAccountInterest(AccountInterest accountInterest) {
        System.out.println("---ACCOUNT INTEREST---");
        System.out.println(accountInterest.getId());
        System.out.println(accountInterest.getTag().name());
        System.out.println("-------------");
    }

    public void printJobs(Job[] jobs) {
        for(Job job: jobs){
            printJob(job);
        }
    }*/

/*    public void printUsers(Account[] accounts) {
        for(Account account: accounts){
            printAccount(account);
        }
    }

    public void printAccountInterests(List<AccountInterest> accountInterests) {
        for(AccountInterest accountInterest: accountInterests){
            printAccountInterest(accountInterest);
        }
    }*/

    public List<JobResponse> getJobs(Long uid) {

        //   1. Get all jobs (List<Job>) and 2. Transform List<Job> -> Array<Job>
       /* Job[] allJobs = this.jobRepository.findAll().toArray(new Job[0]);
        if(allJobs.length == 0){
            return new ArrayList<Job>();
        }*/

        /*TODO: REMOVE LATER*/
/*
        printJobs(allJobs);
*/

        //   3. Get all users (List<Account>) and 4. Transform List<Account> -> Array<Account>
/*
        Account[] allAccounts = this.accountRepository.findAll().toArray(new Account[0]);
*/

        /*TODO: REMOVE LATER*/
/*
        printUsers(allAccounts);
*/

        // 5. Map {Job.id: index in Array<Job>}
        /*HashMap<Long, Integer> jobsMap = new HashMap<Long, Integer>();
        for(int i = 0; i < allJobs.length; i++) {
            jobsMap.put(allJobs[i].getId(), i);
        }*/

        // 6. Map {Account.id: index in Array<Account>}
        /*HashMap<Long, Integer> accountsMap = new HashMap<Long, Integer>();
        for(int i = 0; i < allAccounts.length; i++) {
            accountsMap.put(allAccounts[i].getId(), i);
        }
*/
        // Get user's tags
        /*List<AccountInterest> userAccountTags = this.accountInterestsService.findInterestsByAccountId(uid);
        int K = userAccountTags.size();
*/
        /*TODO: REMOVE LATER*/
/*
        printAccountInterests(userAccountTags);
*/

        // 7. Get all job views (List<JobView>)
/*
        List<JobView> allJobViews = this.jobViewRepository.findAll();
*/
        //      7.1 If list of job views is empty, create an array of tuples (0, job-id) for all the jobs and proceed to (13).
        /*if(allJobViews.isEmpty()) {
            ArrayList<Pair<Long, Long>> views_JobId_Tuples = new ArrayList<>();
            for(Job job: allJobs) {
                views_JobId_Tuples.add(new Pair<Long, Long>(0L, job.getId()));
            }
            return this.tagFilter(views_JobId_Tuples, userAccountTags, allJobs, jobsMap);
        }*/

        /*
         *   8. Make a zeroed 2D matrix, where   (index in x axis === index in array of Jobs),
         *                                       (index in y axis === index in array of Accounts)
         *                                   and ( {x, y} === Job view with job.id: Jobs[x].id and viewer.id: Accounts[y].id )
         *                                   and fill up the existing views (traverse the list of views and use the hash tables
         *                                   to fill up the matrix).
         */
         /*float[][] matrixToFactorize = new float[allJobs.length][allAccounts.length];
         for(int i = 0; i < allJobs.length; i++) {
             for(int j = 0; j < allAccounts.length; j++) {
                matrixToFactorize[i][j] = 0;
             }
         }
         for(JobView view: allJobViews) {
             matrixToFactorize[jobsMap.get(view.getJob().getId())][accountsMap.get(view.getViewer().getId())] = (float)view.getTimes();
         }

         // 9. If Current user has seen EVERY job, (his row has NO zeros), proceed to (11).
         int currUserIndex = accountsMap.get(uid);
         boolean hasSeenAllJobs = true;
         for(float viewCount: matrixToFactorize[currUserIndex]) {
             if(viewCount == 0) {
                 hasSeenAllJobs = false;
                 break;
             }
         }*/
        /*
         * R =  matrixToFactorize
         * P, Q = Random arrays
         * K = User's tags
         */
       /* int N = matrixToFactorize.length;
        int M = matrixToFactorize[0].length;
        float[][] P = new float[N][K];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < K; j++) {
                P[i][j] = ((float) (Math.random() * 10));
            }
        }
        float[][] Q = new float[M][K];
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < K; j++) {
                Q[i][j] = ((float) (Math.random() * 10));
            }
        }*/
        // 10. Run matrix factorization.
/*
        float[][] producedMatrix = new MF().matrix_factorization(matrixToFactorize, P, Q, K);
*/

        // 11. Get the row of current user (M[i] s.t Accounts[i].id === MY ID).
        // 12. From the previous row, create an array of tuples (views, job-id).
        /*ArrayList<Pair<Long, Long>> views_JobId_Tuples = new ArrayList<>();
        int i = 0;
        for(float viewCount: producedMatrix[currUserIndex]) {
            views_JobId_Tuples.add(new Pair<Long, Long>((long)viewCount, allJobs[i++].getId()));
        }
        return tagFilter(views_JobId_Tuples, userAccountTags, allJobs, jobsMap);*/
        return this.jobRepository.findAll().stream().map(jobMapper::JobToJobResponse).collect(Collectors.toList());
    }

    public JobResponse addJob(Job job) {
        List<Job> savedJob = new ArrayList<>();
        savedJob.add(this.jobRepository.save(job));
        return savedJob.stream().map(jobMapper::JobToJobResponse).collect(Collectors.toList()).get(0);
    }

    public JobResponse updateJob(Long jobId, JobRequest jobr) {
        Optional<Job> existingJob = this.jobRepository.findById(jobId);
        if(existingJob.isPresent()) {
            existingJob.get().setTitle(jobr.getTitle());
            existingJob.get().setLocation(jobr.getLocation());
            existingJob.get().setJobType(jobr.getJobType());
            existingJob.get().setExperienceLevel(jobr.getExperienceLevel());
            existingJob.get().setInfo(jobr.getInfo());
        } else throw new IllegalStateException("Job not found!");
        List<Job> job = new ArrayList<>();
        job.add(this.jobRepository.save(existingJob.get()));
        return job.stream().map(jobMapper::JobToJobResponse).collect(Collectors.toList()).get(0);
    }

    public void deleteJob(Long id) {
        this.jobRepository.deleteById(id);
    }
/*
    public Job addTag(String tagName, Long id) {

        Job job = findJobById(id);
        Tag existingTag = this.tagService.getTagByName(tagName);
        if(existingTag == null) {
            job.getTags().add(this.tagService.addTag(new Tag(tagName)));
        } else {
            job.getTags().add(existingTag);
        }
        return this.jobRepository.save(job);

     }*/
}