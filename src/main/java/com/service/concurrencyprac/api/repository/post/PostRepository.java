package com.service.concurrencyprac.api.repository.post;

import com.service.concurrencyprac.api.domain.post.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long>, PostRepositoryCustom {

    Optional<Post> findByPostToken(String postToken);

    List<Post> findAllByOrderByCreatedAtDesc();
}
