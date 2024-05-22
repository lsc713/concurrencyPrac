package com.service.concurrencyprac.payment.repository.post;

import com.service.concurrencyprac.payment.entity.post.PostCommand.PostingCommand;
import com.service.concurrencyprac.payment.entity.post.PostInfo;
import java.util.List;

public interface PostService {

    PostInfo registerPost(PostingCommand postCommand, String username);

    PostInfo getPostInfo(String postToken);

    List<PostInfo> fetchAllPosts();
}
