package com.service.concurrencyprac.api.domain.post;

import com.service.concurrencyprac.api.domain.post.PostCommand.PostingCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostStore postStore;
    private final PostReader postReader;

    @Override
    public PostInfo registerPost(PostingCommand postCommand, String username) {
        Post entity = postCommand.toEntity();
        Post storedPost = postStore.store(entity);
        return new PostInfo(storedPost);
    }
}
