package com.service.concurrencyprac.api.domain.post;

import com.service.concurrencyprac.api.domain.post.PostCommand.PostingCommand;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostStore postStore;
    private final PostReader postReader;

    @Override
    @Transactional
    public PostInfo registerPost(PostingCommand postCommand, String username) {
        Post entity = postCommand.toEntity();
        Post storedPost = postStore.store(entity);
        return new PostInfo(storedPost);
    }

    @Override
    @Transactional(readOnly = true)
    public PostInfo getPostInfo(String postToken) {
        Post post = postReader.getPost(postToken);
        return new PostInfo(post);
    }

    @Override
    public List<PostInfo> fetchAllPosts() {
        List<PostInfo> fetchAllPosts = new ArrayList<>();

        List<Post> allPosts = postReader.getAllPosts();
        for (Post allPost : allPosts) {
            PostInfo postInfo = new PostInfo(allPost);
            fetchAllPosts.add(postInfo);
        }
        return fetchAllPosts;
    }
}
