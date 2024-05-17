package com.service.concurrencyprac.api.repository.post;

import com.service.concurrencyprac.api.domain.post.Post;
import java.util.List;

public interface PostRepositoryCustom {

    List<Post> searchItems(String keyword);

}
