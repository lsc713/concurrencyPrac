package com.service.concurrencyprac.common.image.repository;

import com.service.concurrencyprac.common.image.entity.Image;
import com.service.concurrencyprac.payment.entity.post.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByPost(Post post);
}
