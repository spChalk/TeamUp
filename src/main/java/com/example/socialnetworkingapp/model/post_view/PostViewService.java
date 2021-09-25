package com.example.socialnetworkingapp.model.post_view;

import com.example.socialnetworkingapp.mapper.PostViewMapper;
import com.example.socialnetworkingapp.model.account.Account;
import com.example.socialnetworkingapp.model.post.Post;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostViewService {

    private final PostViewRepository postViewRepository;
    private final PostViewMapper postViewMapper;

    public List<PostViewResponse> getViewsByPostId(Long id) {
        Optional<List<PostView>> views = this.postViewRepository.getViewsByPostId(id);
        if(!views.isPresent()) {
            throw new IllegalStateException("Post with id: " + id.toString() + " does not exist!");
        }
        return views.get().stream().map(postViewMapper::PostViewToPostViewResponse).collect(Collectors.toList());
    }

    public Long getSumOfViewsOfPostByUser(Long uid, Long pid) {
        Optional<PostView> view = this.postViewRepository.getSumOfViewsOfPostByUser(uid, pid);
        if(view.isPresent()) {
            return view.get().getTimes();
        }
        return 0L;
    }

    public Long getSumOfViewsByPostId(Long id) {
        List<PostView> views = this.postViewRepository.getViewsByPostId(id).orElseThrow(
                () -> new IllegalStateException("Post with id: " + id.toString() + " does not exist!")
        );
        Long sum = 0L;
        for(PostView view: views) {
            sum += view.getTimes();
        }
        return sum;
    }

    public PostView addView(Account account, Long jid, Post post) {

        Optional<PostView> jv = this.postViewRepository.findViewByIds(account.getId(), jid);
        if(jv.isPresent()) {
            jv.get().increaseViews();
            return this.postViewRepository.save(jv.get());
        }
        return this.postViewRepository.save(new PostView(
                account,
                post));
    }

    public void deleteViewById(Long id) {
        this.postViewRepository.deleteById(id);
    }
}