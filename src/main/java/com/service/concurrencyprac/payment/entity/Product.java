package com.service.concurrencyprac.payment.entity;

import static com.service.concurrencyprac.common.response.ErrorCode.OUT_OF_STOCK;

import com.service.concurrencyprac.auth.domain.BaseEntity;
import com.service.concurrencyprac.common.exception.InvalidParamException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    private static final String INT_DEFINITION = "int default 0";
    private static final String TEXT_DEFINITION = "text";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 255) // varchar 255 처럼 문자열 지정으로 255자까지 지정가능
    private String name;
    @Column
    private Double price;

    @Column(columnDefinition = INT_DEFINITION) //칼럼 직접지정 int열이며 기본값은 0.
    private int stock;

    @Column(length = 255)
    private String category;

    @Column(length = 1000)
    private String imageUrl;

    @Column(columnDefinition = TEXT_DEFINITION)
    private String description;

    @Column(length = 50)
    private Status status = Status.AVAILABLE;

    @Builder
    public Product(String name, Double price, int stock, String category, String imageUrl,
        String description) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    @Getter
    @RequiredArgsConstructor
    public enum Status {
        AVAILABLE("활성화"), DISABLE("비활성화"),
        ;
        private final String description;
    }

    public void decreaseStock(Integer newStock) {
        if (this.stock - newStock < 0) {
            throw new InvalidParamException(OUT_OF_STOCK);
        }
        this.stock = this.stock - newStock;
    }

    public void increaseStock(Integer newStock) {
        this.stock += newStock;
    }
}
