package com.service.concurrencyprac.security.config;

import com.service.concurrencyprac.auth.jwt.JwtProvider;
import com.service.concurrencyprac.auth.repository.AccessLogRepository;
import com.service.concurrencyprac.security.service.JwtAuthenticationFilter;
import com.service.concurrencyprac.security.service.JwtAuthorizationFilter;
import com.service.concurrencyprac.security.service.UserDetailsServiceImpl;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final AccessLogRepository accessLogRepository;
    private final LoggingAccessDeniedHandler loggingAccessDeniedHandler;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
        throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtProvider,
            accessLogRepository);
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return filter;
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtProvider, userDetailsService);
    }

    @Bean
    public RoleHierarchy roleHierarchy() {

        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("ROLE_ADMIN> ROLE_MANAGER\n" + "ROLE_MANAGER > ROLE-UESR");
        return hierarchy;
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
        security.csrf((auth) -> auth.disable());
        security
            .formLogin((auth) -> auth.disable())
            .httpBasic(AbstractHttpConfigurer::disable);

        security.headers(
            headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        security
            .authorizeRequests((auth) -> auth
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
//                .requestMatchers(PathRequest.toH2Console()).permitAll()
                .requestMatchers("/auth/login").permitAll()
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/api/v1/post/fetch/**").permitAll()
                .requestMatchers("/actuator/health").permitAll()
                .requestMatchers("/admin").hasAnyRole("ADMIN")
                .requestMatchers("/my/**").hasAnyRole("ADMIN", "MANAGER", "USER")
                .anyRequest().authenticated());

        security.exceptionHandling((exceptionHandling) ->
            exceptionHandling.accessDeniedHandler(
            loggingAccessDeniedHandler));

        security
            .sessionManagement(
                (auth) -> auth.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        security.addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);
        security.addFilterBefore(jwtAuthenticationFilter(),
            UsernamePasswordAuthenticationFilter.class);

        security.cors().configurationSource(corsConfigurationSource());
        return security.build();


    }

}
