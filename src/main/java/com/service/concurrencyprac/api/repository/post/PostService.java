package com.service.concurrencyprac.api.repository.post;

import com.service.concurrencyprac.api.domain.post.PostCommand.PostingCommand;
import com.service.concurrencyprac.api.domain.post.PostInfo;
import java.util.List;

public interface PostService {

    PostInfo registerPost(PostingCommand postCommand, String username);

    PostInfo getPostInfo(String postToken);

    List<PostInfo> fetchAllPosts();
}
