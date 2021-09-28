package com.example.socialnetworkingapp.model.job;

import com.example.socialnetworkingapp.mapper.JobMapper;
import com.example.socialnetworkingapp.model.account.Account;
import com.example.socialnetworkingapp.model.job_view.JobView;
import com.example.socialnetworkingapp.model.tags.Tag;
import com.example.socialnetworkingapp.model.job_view.JobViewRepository;
import com.example.socialnetworkingapp.model.tags.TagService;
import com.example.socialnetworkingapp.util.MF;
import com.example.socialnetworkingapp.util.MatrixUtil;
import lombok.AllArgsConstructor;
import org.javatuples.Pair;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class JobService {

    private final JobRepository jobRepository;
    private final JobViewRepository jobViewRepository;
    private final TagService tagService;
    private final JobMapper jobMapper;

    public Job findJobById(Long id) {
        return this.jobRepository.findById(id).
                orElseThrow(() -> new IllegalStateException("Job with id " + id + " was not found!" ));
    }

    private List<JobResponse> tagFilter(ArrayList<Pair<Long, Long>> arrayOfTuples, List<Tag> userAccountTags,
                                JobResponse[] allJobs, HashMap<Long, Integer> jobsMap, Boolean weightTagsAsOne) {
        // 13. TAG FILTERING
        /*
         *   14. For every Job with id === tuple.job-id, see how many tags the job and the user have in common
         *           and for every tag, add an amount to views.
         */
        for(int i = 0; i < arrayOfTuples.size(); i++) {
            List<Tag> jobTags = allJobs[i].getTags();
            for(Tag tag: jobTags) {
                for(Tag accTag: userAccountTags) {
                    if(tag.getTag().equals(accTag.getTag())) {
                        /* For every matching tag, add 10% of current value (if value is non zero) */
                        Long val = arrayOfTuples.get(i).getValue0();
                        if(weightTagsAsOne == false) {
                            arrayOfTuples.set(i, arrayOfTuples.get(i).setAt0(val + (10 * val)/100));
                        } else {
                            arrayOfTuples.set(i, arrayOfTuples.get(i).setAt0(val + 1));
                        }
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
        arrayOfTuples.sort(comparator);

        // 16. Make an empty list, iterate the tuple array, and push() all the Job classes s.t Job.id == tuple.job-id
        List<JobResponse> result = new ArrayList<>();
        for(Pair<Long, Long> tuple: arrayOfTuples) {
            result.add(0, allJobs[jobsMap.get(tuple.getValue1())]);
        }
        // 17. Return the list of jobs.
        return result;
    }

    public List<Job> getJobsUnsorted(Long userId) {
        return this.jobRepository.findJobsByPublisherId(userId);
    }

    public List<JobResponse> getJobs(Account user) {

        //   1. Get all jobs (List<Job>) and 2. Transform List<Job> -> Array<Job>
        JobResponse[] allJobs = this.jobRepository.findAll().stream().map(jobMapper::JobToJobResponse).toArray(JobResponse[]::new);
        if(allJobs.length == 0){
            return new ArrayList<JobResponse>();
        }

        //   3. Get all users from network (List<Account>) and 4. Transform List<Account> -> Array<Account>
        List<Account> allAccountsList = new ArrayList<>(user.getNetwork());
        allAccountsList.add(user);
        Account[] allAccounts = allAccountsList.toArray(new Account[0]);

        // 5. Map {Job.id: index in Array<Job>}
        HashMap<Long, Integer> jobsMap = new HashMap<Long, Integer>();
        for(int i = 0; i < allJobs.length; i++) {
            jobsMap.put(allJobs[i].getId(), i);
        }

        // 6. Map {Account.id: index in Array<Account>}
        HashMap<Long, Integer> accountsMap = new HashMap<Long, Integer>();
        for(int i = 0; i < allAccounts.length; i++) {
            accountsMap.put(allAccounts[i].getId(), i);
        }

        // Get user's tags
        int K = user.getTags().size();

        // 7. Get all job views (List<JobView>)
        List<JobView> allJobViews = this.jobViewRepository.findAll();

        //      7.1 If list of job views is empty, create an array of tuples (0, job-id) for all the jobs and proceed to (13).
        if(allJobViews.isEmpty()) {
            ArrayList<Pair<Long, Long>> views_JobId_Tuples = new ArrayList<>();
            for(JobResponse job: allJobs) {
                views_JobId_Tuples.add(new Pair<Long, Long>(0L, job.getId()));
            }
            return this.tagFilter(views_JobId_Tuples, user.getTags(), allJobs, jobsMap, true);
        }
        /*
         *   8. Make a zeroed 2D matrix, where   (index in x axis === index in array of Jobs),
         *                                       (index in y axis === index in array of Accounts)
         *                                   and ( {x, y} === Job view with job.id: Jobs[x].id and viewer.id: Accounts[y].id )
         *                                   and fill up the existing views (traverse the list of views and use the hash tables
         *                                   to fill up the matrix).
         */
         float[][] matrixToFactorize = new float[allAccounts.length][allJobs.length];
         for(int i = 0; i < allAccounts.length; i++) {
             for(int j = 0; j < allJobs.length; j++) {
                matrixToFactorize[i][j] = 0.0f;
             }
         }

        for(JobView view: allJobViews) {
            if(accountsMap.containsKey(view.getViewer().getId()) && jobsMap.containsKey(view.getJob().getId())) {
                matrixToFactorize[accountsMap.get(view.getViewer().getId())][jobsMap.get(view.getJob().getId())] = (float) view.getTimes();
            }
        }

         // 9. If Current user has seen EVERY job, (his row has NO zeros), proceed to (11).
         int currUserIndex = accountsMap.get(user.getId());
         boolean hasSeenAllJobs = true;
         for(float views: matrixToFactorize[currUserIndex]) {
             if(views == 0) {
                 hasSeenAllJobs = false;
                 break;
             }
         }
        ArrayList<Pair<Long, Long>> views_JobId_Tuples = new ArrayList<>();
        if(hasSeenAllJobs == false) {
             /*
              * R =  matrixToFactorize
              * P, Q = Random arrays
              * K = User's tags
              */
            int N = matrixToFactorize.length;
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
            }

            // 10. Run matrix factorization.
            float[][] producedMatrix = new MF(new MatrixUtil()).matrix_factorization(matrixToFactorize, P, Q, K);

             // 11. Get the row of current user (M[i] s.t Accounts[i].id === MY ID).
             // 12. From the previous row, create an array of tuples (views, job-id).
            int i = 0;
             for(float views: producedMatrix[currUserIndex]) {
                 views_JobId_Tuples.add(new Pair<Long, Long>((long)views, allJobs[i++].getId()));
             }
         }
         else {
             // 11. Get the row of current user (M[i] s.t Accounts[i].id === MY ID).
             // 12. From the previous row, create an array of tuples (views, job-id).
            int i = 0;
             for (float views: matrixToFactorize[currUserIndex]) {
                 views_JobId_Tuples.add(new Pair<Long, Long>((long) views, allJobs[i++].getId()));
             }
         }
        return tagFilter(views_JobId_Tuples, user.getTags(), allJobs, jobsMap, false);
    }

    public JobResponse addJob(Job job) {

        for(Tag tag: job.getTags()) {
            Tag existingTag = this.tagService.getTagByName(tag.getTag());
            if(existingTag != null) {
                job.getTags().set(job.getTags().indexOf(tag), existingTag);
            }
        }

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
            existingJob.get().setTags(jobr.getTags());
        } else throw new IllegalStateException("Job not found!");
        List<Job> job = new ArrayList<>();
        job.add(this.jobRepository.save(existingJob.get()));
        return job.stream().map(jobMapper::JobToJobResponse).collect(Collectors.toList()).get(0);
    }

    public void deleteJob(Long id) {
        this.jobRepository.deleteById(id);
    }
}