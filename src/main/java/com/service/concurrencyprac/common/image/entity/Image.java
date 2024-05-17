package com.service.concurrencyprac.common.image.entity;

import com.service.concurrencyprac.api.domain.post.Post;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private Post post;

    private String originName;//본 이름
    private String storeName;// 이미지파일에 저장될 이름
    private String accessUrl;//내부 이미지에 접근할 수 있는 Url

    @Builder
    public Image(String originName, String savedImage, String accessUrl, Post post) {
        this.originName = originName;
        this.storeName = savedImage;
        this.accessUrl = accessUrl;
        this.post = post;
    }

    public void setAccessUrl(String accessUrl) {
        this.accessUrl = accessUrl;
    }

}
