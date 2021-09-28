package com.example.socialnetworkingapp.model.job;

import com.example.socialnetworkingapp.mapper.JobMapper;
import com.example.socialnetworkingapp.model.account.Account;
import com.example.socialnetworkingapp.model.account.AccountRepository;
import com.example.socialnetworkingapp.model.job_application.JobApplicationRepository;
import com.example.socialnetworkingapp.model.job_view.JobView;
import com.example.socialnetworkingapp.model.tags.Tag;
import com.example.socialnetworkingapp.model.job_view.JobViewRepository;
import com.example.socialnetworkingapp.model.tags.TagService;
import com.example.socialnetworkingapp.util.MF;
import com.example.socialnetworkingapp.util.MatrixUtil;
import lombok.AllArgsConstructor;
import org.javatuples.Pair;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
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
                                JobResponse[] allJobs, HashMap<Long, Integer> jobsMap, FileWriter log, Boolean weightTagsAsOne) throws IOException {
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
                        log.write("Equal Tags! " + " \n");
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
            log.write("Pushing Job " + allJobs[jobsMap.get(tuple.getValue1())].getId() + " with SCORE: " + tuple.getValue0() + "\n");
            result.add(0, allJobs[jobsMap.get(tuple.getValue1())]);
        }
        log.close();
        // 17. Return the list of jobs.
        return result;
    }

    public List<Job> getJobsUnsorted(Long userId) {
        return this.jobRepository.findJobsByPublisherId(userId);
    }

    public List<JobResponse> getJobs(Account user) throws IOException {

        FileWriter log = new FileWriter("GetAllJobsLOG.txt");

        //   1. Get all jobs (List<Job>) and 2. Transform List<Job> -> Array<Job>
        JobResponse[] allJobs = this.jobRepository.findAll().stream().map(jobMapper::JobToJobResponse).toArray(JobResponse[]::new);
        if(allJobs.length == 0){
            log.write("There are 0 jobs. Returning an empty array..");
            log.close();
            return new ArrayList<JobResponse>();
        }

        //   3. Get all users from network (List<Account>) and 4. Transform List<Account> -> Array<Account>
        List<Account> allAccountsList = new ArrayList<>(user.getNetwork());
        allAccountsList.add(user);
        Account[] allAccounts = allAccountsList.toArray(new Account[0]);

        // 5. Map {Job.id: index in Array<Job>}
        HashMap<Long, Integer> jobsMap = new HashMap<Long, Integer>();
        for(int i = 0; i < allJobs.length; i++) {
            log.write("Mapping Job " + allJobs[i].getId() + " to Index " + i + "\n");
            jobsMap.put(allJobs[i].getId(), i);
        }

        // 6. Map {Account.id: index in Array<Account>}
        HashMap<Long, Integer> accountsMap = new HashMap<Long, Integer>();
        for(int i = 0; i < allAccounts.length; i++) {
            log.write("Mapping Account " + allAccounts[i].getId() + " to Index " + i + "\n");
            accountsMap.put(allAccounts[i].getId(), i);
        }

        // Get user's tags
        int K = user.getTags().size();
        log.write("USER " + user.getId() + " HAS " + K + " TAGS." + "\n");

        // 7. Get all job views (List<JobView>)
        List<JobView> allJobViews = this.jobViewRepository.findAll();

        //      7.1 If list of job views is empty, create an array of tuples (0, job-id) for all the jobs and proceed to (13).
        if(allJobViews.isEmpty()) {
            log.write("List of job views is empty!" + "\n");
            ArrayList<Pair<Long, Long>> views_JobId_Tuples = new ArrayList<>();
            for(JobResponse job: allJobs) {
                log.write("Adding pair { " + 0L + ", " + job.getId() + " }" + "\n");
                views_JobId_Tuples.add(new Pair<Long, Long>(0L, job.getId()));
            }
            return this.tagFilter(views_JobId_Tuples, user.getTags(), allJobs, jobsMap, log, true);
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

        log.write("\nCreated zeroed matrix of " + allJobs.length + " x " + allAccounts.length + "\n\n");
        for(int i = 0; i < allAccounts.length; i++) {
            for(int j = 0; j < allJobs.length; j++) {
                log.write(String.valueOf(matrixToFactorize[i][j]));
            }
            log.write("\n");
        }

        log.write("\n Adding views.." + "\n\n");
        for(JobView view: allJobViews) {
            if(accountsMap.containsKey(view.getViewer().getId()) && jobsMap.containsKey(view.getJob().getId())) {
                matrixToFactorize[accountsMap.get(view.getViewer().getId())][jobsMap.get(view.getJob().getId())] = (float) view.getTimes();
                log.write("Added to [" + accountsMap.get(view.getViewer().getId()) + ", " + jobsMap.get(view.getJob().getId()) + "]" + " : " + (float) view.getTimes() + " views\n");
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
            log.write("Creating random arrays....(N = " + N + ") and (M = " + M + ")\n\n");
            float[][] P = new float[N][K];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < K; j++) {
                    P[i][j] = ((float) (Math.random() * 10));
                }
            }
            log.write("P = \n");
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < K; j++) {
                    log.write(String.valueOf(P[i][j]) + "  ");
                }
                log.write("\n");
            }


            float[][] Q = new float[M][K];
            for (int i = 0; i < M; i++) {
                for (int j = 0; j < K; j++) {
                    Q[i][j] = ((float) (Math.random() * 10));
                }
            }
            log.write("\nQ = \n");
            for (int i = 0; i < M; i++) {
                for (int j = 0; j < K; j++) {
                    log.write(String.valueOf(Q[i][j]) + "  ");
                }
                log.write("\n");
            }
            // 10. Run matrix factorization.
            log.write("\n\nRunning matrix factorization...");
            float[][] producedMatrix = new MF(new MatrixUtil()).matrix_factorization(matrixToFactorize, P, Q, K);
            log.write("\n\nProduced matrix:\n");
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    log.write(String.valueOf(producedMatrix[i][j]) + "  ");
                }
                log.write("\n");
            }
             // 11. Get the row of current user (M[i] s.t Accounts[i].id === MY ID).
             // 12. From the previous row, create an array of tuples (views, job-id).
            int i = 0;
             for(float views: producedMatrix[currUserIndex]) {
                 log.write("Adding { views: " + views + ", jobID: " + allJobs[i].getId() + "}\n");
                 views_JobId_Tuples.add(new Pair<Long, Long>((long)views, allJobs[i++].getId()));
             }
         }
         else {
             // 11. Get the row of current user (M[i] s.t Accounts[i].id === MY ID).
             // 12. From the previous row, create an array of tuples (views, job-id).
            int i = 0;
             for (float views: matrixToFactorize[currUserIndex]) {
                 log.write("Adding { views: " + views + ", jobID: " + allJobs[i].getId() + "}\n");
                 views_JobId_Tuples.add(new Pair<Long, Long>((long) views, allJobs[i++].getId()));
             }
         }
        return tagFilter(views_JobId_Tuples, user.getTags(), allJobs, jobsMap, log, false);
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