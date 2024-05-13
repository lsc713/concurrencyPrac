package com.service.consumer.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class FailedEvent {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String userToken;

    public FailedEvent() {

    }

    public FailedEvent(Long userId) {
        this.userId = userId;
    }

}
