package com.service.concurrencyprac.payment.entity;

import com.service.concurrencyprac.api.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;

@Entity
@Getter
public class ShippingInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "shippingInfo")
    private Order order;

    @Column(length = 1000)
    private String address;

    //TODO:배송상태관련 enumType만들기
    @Column(length = 50)
    private String status;

    @Column(length = 100, nullable = true)
    private String trackingNumber;

    @Column(length = 100, nullable = true)
    private String shippingCompany;
}
