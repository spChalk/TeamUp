package com.example.socialnetworkingapp.model.post;

import com.example.socialnetworkingapp.mapper.PostMapper;
import com.example.socialnetworkingapp.model.account.Account;
import com.example.socialnetworkingapp.model.comment.CommentResponse;
import com.example.socialnetworkingapp.model.comment.CommentService;
import com.example.socialnetworkingapp.model.like.Like;
import com.example.socialnetworkingapp.model.like.LikeResponse;
import com.example.socialnetworkingapp.model.like.LikeService;
import com.example.socialnetworkingapp.model.post_view.PostView;
import com.example.socialnetworkingapp.model.post_view.PostViewRepository;
import com.example.socialnetworkingapp.model.post_view.PostViewResponse;
import com.example.socialnetworkingapp.model.post_view.PostViewService;
import com.example.socialnetworkingapp.util.MF;
import com.example.socialnetworkingapp.util.MatrixUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.javatuples.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final LikeService likeService;
    private final CommentService commentService;
    private final PostViewService postViewService;
    private final PostViewRepository postViewRepository;

    public PostResponse addPost(Post post){

        List<Post> saved = new ArrayList<>();
        saved.add(this.postRepository.save(post));
        return saved.stream().map(postMapper::PostToPostResponse).collect(Collectors.toList()).get(0);
    }

    public List<PostResponse> getDateSortedPosts(Account user) {
        /* Get user's network */
        List<Account> userNetwork = user.getNetwork();
        /* Get user's posts */
        List<PostResponse> postsToReturn = this.findPostsByAuthorId(user.getId());

        /* If user has no friends, return only the posts he has made */
        if(userNetwork.isEmpty()) {
            return postsToReturn
                    .stream()
                    .sorted(Comparator.comparing(PostResponse::getDate).reversed())
                    .collect(Collectors.toList());
        }

        /* For every user's connection in network */
        for(Account contact: userNetwork) {

            /* Get the posts of user's connections and add them to the list */
            List<PostResponse> friendPosts = this.findPostsByAuthorId(contact.getId());
            if(!friendPosts.isEmpty()) {
                postsToReturn.addAll(friendPosts);
            }

            /* Get user's connections' likes and extract the liked posts to 'likedPostsByFriend' */
            List<Like> friendLikes = this.likeService.findLikesByUserId(contact.getId());
            List<Post> likedPostsByFriend = new ArrayList<>();
            for(Like like: friendLikes) {
                likedPostsByFriend.add(like.getPost());
            }
            /* Add the posts to the list */
            if(!likedPostsByFriend.isEmpty()) {
                postsToReturn.addAll(likedPostsByFriend
                        .stream().map(postMapper::PostToPostResponse).collect(Collectors.toList()));
            }
        }
        /* Return the posts without duplicates and with the latest posts first! */
        return postsToReturn.stream().distinct().collect(Collectors.toList())
                .stream()
                .sorted(Comparator.comparing(PostResponse::getDate).reversed())
                .collect(Collectors.toList());
    }

    /* There are no likes in posts FROM MY NETWORK users */
    public Boolean thereAreNoLikesIn(Account user, List<PostResponse> posts) {
        for(PostResponse post: posts) {
            List<LikeResponse> allLikes = this.likeService.findAllLikesOfPost(post.getId());
            for(LikeResponse like: allLikes) {
                /* If I have seen it, return false */
                if(Objects.equals(user.getEmail(), like.getUserEmail())) {
                    return false;
                }
                /* Else, seek for views from my network */
                for(Account friend: user.getNetwork()) {
                    if(Objects.equals(friend.getEmail(), like.getUserEmail())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /* There are no comments in posts FROM MY NETWORK users */
    public Boolean thereAreNoCommentsIn(Account user, List<PostResponse> posts) {
        for(PostResponse post: posts) {
            List<CommentResponse> allComments = this.commentService.findAllCommentsOfPost(post.getId());
            for(CommentResponse comment: allComments) {
                /* If I have seen it, return false */
                if(Objects.equals(user.getEmail(), comment.getAuthorEmail())) {
                    return false;
                }
                /* Else, seek for views from my network */
                for(Account friend: user.getNetwork()) {
                    if(Objects.equals(friend.getEmail(), comment.getAuthorEmail())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /* There are no views in posts FROM MY NETWORK users */
    public Boolean haveNoViews(Account user, List<PostResponse> posts) {
        for(PostResponse post: posts) {
            List<PostViewResponse> views = this.postViewService.getViewsByPostId(post.getId());
            for(PostViewResponse view: views) {
                /* If I have seen it, return false */
                if(Objects.equals(user.getEmail(), view.getEmail())) {
                    return false;
                }
                /* Else, seek for views from my network */
                for(Account friend: user.getNetwork()) {
                    if(Objects.equals(friend.getEmail(), view.getEmail())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public Boolean hasSeenAllOf(Account user, List<PostResponse> posts) {
        for(PostResponse post: posts) {
            List<PostViewResponse> views = this.postViewService.getViewsByPostId(post.getId());
            boolean hasSeenThis = false;
            for(PostViewResponse view: views) {
                if(Objects.equals(user.getEmail(), view.getEmail())) {
                    hasSeenThis = true;
                    break;
                }
            }
            if(hasSeenThis == false) {
                return false;
            }
        }
        return true;
    }

    public List<PostResponse> sortPostsByNetwork(Account user, List<PostResponse> posts) {
        List<PostResponse> userPosts = new ArrayList<>();
        List<PostResponse> networkPosts = new ArrayList<>();
        List<PostResponse> otherPosts = new ArrayList<>();
        for(PostResponse post: posts) {
            if(Objects.equals(post.getAuthorEmail(), user.getEmail())) {
                userPosts.add(post);
                continue;
            }
            boolean isNetworks = false;
            for(Account friend: user.getNetwork()) {
                if(Objects.equals(friend.getEmail(), post.getAuthorEmail())) {
                    networkPosts.add(post);
                    isNetworks = true;
                    break;
                }
            }
            if(isNetworks == false) {
                otherPosts.add(post);
            }
        }
        /* Concatenate the 3 lists: {sorted network list}{sorted non network list}{sorted user's list} */
        List<PostResponse> temp = Stream.concat(networkPosts.stream(), otherPosts.stream()).collect(Collectors.toList());
        return Stream.concat(temp.stream(), userPosts.stream()).collect(Collectors.toList());
    }

    public ArrayList<Pair<Long, Long>> runMatrixFactorization(List<PostResponse> dateSortedPosts, Account user, FileWriter log) throws IOException {
        //   1. Get all jobs (List<Post>) and 2. Transform List<Post> -> Array<Post>
        PostResponse[] allPosts = dateSortedPosts.toArray(new PostResponse[0]);

        //   3. Get all users from network (List<Account>) and 4. Transform List<Account> -> Array<Account>
        List<Account> allAccountsList = new ArrayList<>(user.getNetwork());
        allAccountsList.add(user);
        Account[] allAccounts = allAccountsList.toArray(new Account[0]);

        // 5. Map {Post.id: index in Array<Job>}
        HashMap<Long, Integer> postsMap = new HashMap<Long, Integer>();
        for(int i = 0; i < allPosts.length; i++) {
            log.write("Mapping Post " + allPosts[i].getId() + " to Index " + i + "\n");
            postsMap.put(allPosts[i].getId(), i);
        }

        // 6. Map {Account.id: index in Array<Account>}
        HashMap<Long, Integer> accountsMap = new HashMap<Long, Integer>();
        for(int i = 0; i < allAccounts.length; i++) {
            log.write("Mapping Account " + allAccounts[i].getId() + " to Index " + i + "\n");
            accountsMap.put(allAccounts[i].getId(), i);
        }

        // 7. Get all post views (List<PostView>)
        List<PostView> allPostViews = this.postViewRepository.findAll();

        // Get user's tags
        int K = user.getTags().size();
        log.write("USER " + user.getId() + " HAS " + K + " TAGS." + "\n");

        /*
         *   8. Make a zeroed 2D matrix, where   (index in x axis === index in array of Posts),
         *                                       (index in y axis === index in array of Accounts)
         *                                   and ( {x, y} === Post view with post.id: Posts[x].id and viewer.id: Accounts[y].id )
         *                                   and fill up the existing views (traverse the list of views and use the hash tables
         *                                   to fill up the matrix).
         */
        float[][] matrixToFactorize = new float[allAccounts.length][allPosts.length];
        for(int i = 0; i < allAccounts.length; i++) {
            for(int j = 0; j < allPosts.length; j++) {
                matrixToFactorize[i][j] = 0.0f;
            }
        }

        log.write("\nCreated zeroed matrix of " + allPosts.length + " x " + allAccounts.length + "\n\n");
        for(int i = 0; i < allAccounts.length; i++) {
            for(int j = 0; j < allPosts.length; j++) {
                log.write(String.valueOf(matrixToFactorize[i][j]));
            }
            log.write("\n");
        }

        log.write("\n Adding views.." + "\n\n");
        for(PostView view: allPostViews) {
            if(accountsMap.containsKey(view.getViewer().getId()) && postsMap.containsKey(view.getPost().getId())) {
                matrixToFactorize[accountsMap.get(view.getViewer().getId())][postsMap.get(view.getPost().getId())] = (float)view.getTimes();
                log.write("Added to [" + accountsMap.get(view.getViewer().getId()) + ", " + postsMap.get(view.getPost().getId()) + "]" + " : " + (float)view.getTimes() + " views\n");
            }
        }
        int currUserIndex = accountsMap.get(user.getId());
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
        // 12. From the previous row, create an array of tuples (views, post-id).
        ArrayList<Pair<Long, Long>> views_PostId_Tuples = new ArrayList<>();
        int i = 0;
        for(float views: producedMatrix[currUserIndex]) {
            log.write("Adding { views: " + views + ", postID: " + allPosts[i].getId() + "}\n");
            views_PostId_Tuples.add(new Pair<Long, Long>((long)views, allPosts[i++].getId()));
        }
        return views_PostId_Tuples;
    }

    public ArrayList<Pair<Long, Long>> addLikeCommentPercentage (Long userId, ArrayList<Pair<Long, Long>> tuples) {
        for(int i = 0; i < tuples.size(); i++) {
            List<CommentResponse> comments = this.commentService.findCommentsOfUserInAPost(userId, tuples.get(i).getValue1());
            Like like = this.likeService.findLikeOfUserInAPost(userId, tuples.get(i).getValue1());
            Long val = tuples.get(i).getValue0();
            if(comments.isEmpty() == false) {
                tuples.set(i, tuples.get(i).setAt0(val + (50 * val)/100));
            }
            if(like != null) {
                tuples.set(i, tuples.get(i).setAt0(2 * val));
            }
        }
        return tuples;
    }

    /** FIND ALL POSTS
     * ----------------
     * 1. Gather all posts (P) that:
     *      a. A user has made.
     *      b. A user's friend has made.
     *      c. A user's friend has liked.
     *
     * 2. Sort by creation date (latest first).
     *
     * 3. If P have NO like and NO comment:
     *      a. If P have NO views OR current user has seen all of P:
     *          i. Sort by {network}{non network}{user's posts} by parsing the already sorted list of posts and RETURN.
     *      b. If P's views are non-empty:
     *          i. Run Matrix Factorization.
     *
     * 4. If P have likes or/and comments:
     *      a. If current user has NOT seen all of P:
     *          i. Run Matrix Factorization.
     *
     *      b. Parse the posts and:
     *          i. For each like add 100% of views.
     *          ii. For each comment add 50% of views.
     *
     * 5. Sort by most viewed.
     * 6. Sort by {network}{non network}{user's posts} by parsing the already sorted list of posts and RETURN.
     */
    public List<PostResponse> findAllPosts(Account user) throws IOException {

        FileWriter log = new FileWriter("GetAllPostsLOG.txt");

        List<PostResponse> dateSortedPosts = getDateSortedPosts(user);
        if(dateSortedPosts.isEmpty()) {
            log.write("There are 0 posts. Returning an empty array..");
            log.close();
            return new ArrayList<PostResponse>();
        }

        ArrayList<Pair<Long, Long>> listOfTuples;
        if(thereAreNoLikesIn(user, dateSortedPosts) && thereAreNoCommentsIn(user, dateSortedPosts)) {
            if(haveNoViews(user, dateSortedPosts) || hasSeenAllOf(user, dateSortedPosts)) {
                return sortPostsByNetwork(user, dateSortedPosts);
            } else {
                listOfTuples = runMatrixFactorization(dateSortedPosts, user, log);
            }
        } else {
            if(hasSeenAllOf(user, dateSortedPosts) == false) {
                listOfTuples = runMatrixFactorization(dateSortedPosts, user, log);
            } else {
                listOfTuples = new ArrayList<>();
                for (PostResponse post : dateSortedPosts) {
                    Long views = this.postViewService.getSumOfViewsOfPostByUser(user.getId(), post.getId());
                    log.write("Adding { views: " + views + ", postID: " + post.getId() + "}\n");
                    listOfTuples.add(new Pair<Long, Long>((long) views, post.getId()));
                }
            }
            listOfTuples = addLikeCommentPercentage(user.getId(), listOfTuples);
        }
        /* Sort by most viewed */
        Comparator<Pair<Long, Long>> comparator = new Comparator<Pair<Long, Long>>() {
            public int compare(Pair<Long, Long> tupleA,
                               Pair<Long, Long> tupleB) {
                return tupleA.getValue0().compareTo(tupleB.getValue0());
            }
        };
        listOfTuples.sort(comparator);
        List<Post> finalPostList = new ArrayList<>();
        for(Pair<Long, Long> tuple: listOfTuples) {
            finalPostList.add(findPostById(tuple.getValue1()));
        }
        return sortPostsByNetwork(user, finalPostList.stream().map(postMapper::PostToPostResponse).collect(Collectors.toList()));
    }

    public List<PostResponse> findPostsByAuthorId(Long id) {
        return this.postRepository.findPostsByAuthorId(id).stream().map(postMapper::PostToPostResponse).collect(Collectors.toList());
    }

    public void deletePost(Long id){
        this.postRepository.deletePostById(id);
    }

    public Post findPostById(Long id){
        return postRepository.findPostById(id).orElseThrow( () -> new IllegalStateException("Post with id "+ id.toString() + " was not found !"));
    }

    public Post updatePost(Post p) {

        Optional<Post> postPresent = this.postRepository.findPostById(p.getId());
        if(!postPresent.isPresent()) {
            throw new IllegalStateException("Post with id " + p.getId() + " does not exist!");
        }

        Post post = postPresent.get();
        post.setPayload(p.getPayload());
        post.setAuthor(p.getAuthor());
        post.setDate(p.getDate());
        post.setImagePath(p.getImagePath());
        post.setVideoPath(p.getVideoPath());
        post.setSoundPath(p.getSoundPath());
        return this.postRepository.save(post);
    }
}
