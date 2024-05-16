package com.service.concurrencyprac.api.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.service.concurrencyprac.api.domain.coupon.ApplyService;
import com.service.concurrencyprac.api.repository.coupon.CouponRepository;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplyServiceTest {

    @Autowired
    private ApplyService applyService;

    @Autowired
    private CouponRepository couponRepository;

    @Test
    public void 한번만응모() {
        applyService.apply(1L);

        long count = couponRepository.count();

        assertThat(count).isEqualTo(1);
    }

    @Test
    public void 여러명응모() throws Exception {
        //given
        int threadCount = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            long userId = i;
            executorService.submit(() -> {
                try {
                    applyService.apply(userId);

                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        Thread.sleep(10000);

        long count = couponRepository.count();

        assertThat(count).isEqualTo(100);
    }

    @Test
    public void 한명당_한개의쿠폰_발급() throws Exception {
        //given
        int threadCount = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            long userId = i;
            executorService.submit(() -> {
                try {
                    applyService.apply(1L);

                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        Thread.sleep(10000);

        long count = couponRepository.count();

        assertThat(count).isEqualTo(1);
    }

}