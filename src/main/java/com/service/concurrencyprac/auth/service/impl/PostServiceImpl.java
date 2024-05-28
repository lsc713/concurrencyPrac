package com.service.concurrencyprac.auth.service.impl;

import com.service.concurrencyprac.payment.dto.PostDTO.PostUpdateDto;
import com.service.concurrencyprac.payment.entity.post.Post;
import com.service.concurrencyprac.payment.entity.post.PostCommand.PostingCommand;
import com.service.concurrencyprac.payment.entity.post.PostInfo;
import com.service.concurrencyprac.payment.entity.post.PostReader;
import com.service.concurrencyprac.payment.entity.post.PostStore;
import com.service.concurrencyprac.payment.repository.post.PostService;
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
    @Transactional(readOnly = true)
    public List<PostInfo> fetchAllPosts() {
        return postReader.getAllPosts().stream()
            .map(PostInfo::new)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PostInfo updatePost(String postToken, PostUpdateDto requestDto) {
        Post post = postReader.getPost(postToken);
        post.update(requestDto);
        return new PostInfo(post);
    }

    @Override
    public void deletePost(String postToken, String username) {
        Post post = postReader.getPost(postToken);
        postStore.delete(post);
    }
}
