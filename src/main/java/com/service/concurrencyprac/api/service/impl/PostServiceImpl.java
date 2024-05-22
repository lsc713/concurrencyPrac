package com.service.concurrencyprac.api.service.impl;

import com.service.concurrencyprac.api.domain.post.Post;
import com.service.concurrencyprac.api.domain.post.PostCommand.PostingCommand;
import com.service.concurrencyprac.api.domain.post.PostInfo;
import com.service.concurrencyprac.api.domain.post.PostReader;
import com.service.concurrencyprac.api.domain.post.PostStore;
import com.service.concurrencyprac.api.repository.post.PostService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

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
        return postReader.getAllPosts().stream()
            .map(PostInfo::new)
            .collect(Collectors.toList());
    }
}
