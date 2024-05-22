package com.service.concurrencyprac.payment.repository.post;

import com.service.concurrencyprac.payment.entity.post.Post;
import java.util.List;

public interface PostRepositoryCustom {

    List<Post> searchItems(String keyword);

}
