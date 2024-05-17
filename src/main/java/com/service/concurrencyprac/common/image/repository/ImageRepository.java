package com.service.concurrencyprac.common.image.repository;

import com.service.concurrencyprac.common.image.entity.Image;
import java.util.List;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByItem(Item item);
}
