package com.service.concurrencyprac.payment.service.impl;

import com.service.concurrencyprac.common.exception.InvalidParamException;
import com.service.concurrencyprac.payment.entity.Point;
import com.service.concurrencyprac.payment.entity.PointLog;
import com.service.concurrencyprac.payment.entity.PointLog.Type;
import com.service.concurrencyprac.payment.repository.coupon.PointLogRepository;
import com.service.concurrencyprac.payment.repository.coupon.PointRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PointServiceImplTest {

    @InjectMocks
    private PointServiceImpl pointService;

    @Mock
    private PointRepository pointRepository;

    @Mock
    private PointLogRepository pointLogRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUsePoint() {
        // Given
        Point point = Point.builder()
            .id(1L)
            .availableAmount(1000)
            .logs(new ArrayList<>())
            .build();

        int amountToUse = 100;
        String reason = "Purchase";
        Type type = Type.SPEND;

        when(pointRepository.save(any(Point.class))).thenReturn(point);
        when(pointLogRepository.save(any(PointLog.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        pointService.usePoint(point, amountToUse, reason, type);

        // Then
        ArgumentCaptor<Point> pointCaptor = ArgumentCaptor.forClass(Point.class);
        verify(pointRepository).save(pointCaptor.capture());
        Point savedPoint = pointCaptor.getValue();

        assertEquals(900, savedPoint.getAvailableAmount());

        ArgumentCaptor<PointLog> pointLogCaptor = ArgumentCaptor.forClass(PointLog.class);
        verify(pointLogRepository).save(pointLogCaptor.capture());
        PointLog savedLog = pointLogCaptor.getValue();

        assertNotNull(savedLog);
        assertEquals(amountToUse, savedLog.getAmount());
        assertEquals(reason, savedLog.getReason());
        assertEquals(Type.SPEND, savedLog.getType());
        assertEquals(point, savedLog.getPoint());
    }

    @Test
    public void testUsePoint_InsufficientFunds() {
        // Given
        Point point = Point.builder()
            .id(1L)
            .availableAmount(50)
            .logs(new ArrayList<>())
            .build();

        int amountToUse = 100;
        String reason = "Purchase";

        // When & Then
        assertThrows(InvalidParamException.class, () -> {
            pointService.usePoint(point, amountToUse, reason, Type.SPEND);
        });
    }
}