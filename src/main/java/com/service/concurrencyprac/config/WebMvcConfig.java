package com.service.concurrencyprac.config;

import com.querydsl.core.annotations.Config;
import com.service.concurrencyprac.common.logging.CommonHttpRequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final CommonHttpRequestInterceptor requestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestInterceptor)
            .order(1)
            .addPathPatterns("/**")
            .excludePathPatterns("/error");
    }

}
