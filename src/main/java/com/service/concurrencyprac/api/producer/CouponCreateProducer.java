package com.service.concurrencyprac.api.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class CouponCreateProducer {

    private static final String COUPON_CREATE_PRODUCER_TOPIC = "coupon_create";
    private final KafkaTemplate<String, Long> kafkaTemplate;

    public CouponCreateProducer(KafkaTemplate<String, Long> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void create(Long userId) {
        kafkaTemplate.send(COUPON_CREATE_PRODUCER_TOPIC, userId);
    }
}
