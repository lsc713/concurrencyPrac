package com.service.concurrencyprac.api.repository.post;

import com.service.concurrencyprac.api.domain.post.Post;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {

    Optional<Post> findByPostToken(String postToken);
}
