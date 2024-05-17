package com.service.concurrencyprac.api.domain.post;

import com.service.concurrencyprac.api.domain.post.PostCommand.PostingCommand;

public interface PostService {

    PostInfo registerPost(PostingCommand postCommand, String username);

    PostInfo getPostInfo(String postToken);
}
