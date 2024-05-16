package com.service.concurrencyprac.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((auth) -> auth.disable());
        http
            .formLogin((auth) -> auth
                .loginPage("/login")//로그인 페이지
                .loginProcessingUrl("/loginProc")
                .permitAll());//괄호안에 있는 곳에서 넘어오면 로그인 진행

        http
            .authorizeRequests((auth) -> auth.requestMatchers("/", "/login", "/signup").permitAll()
                .requestMatchers("/admin").hasRole("ADMIN")
                .requestMatchers("/my/**").hasAnyRole("ADMIN","USER")
                .anyRequest().authenticated());

        http
            .sessionManagement((auth) -> auth.maximumSessions(1)
                .maxSessionsPreventsLogin(true));//추가로그인시 이전 세션무효화
        http
            .sessionManagement((auth) -> auth.sessionFixation()
                .changeSessionId());//세션고정공격 방지를 위해 세션아이디변경

        return http.build();
    }
}
