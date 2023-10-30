package com.cnusw.balancetalk.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.cnusw.balancetalk.global.jwt.JwtAuthenticationEntryPoint;

import lombok.RequiredArgsConstructor;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity // 기본적인 웹 보안 활성화
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // token을 사용하는 방식이기 때문에 csrf를 disable로 설정한다.
                .csrf(AbstractHttpConfigurer::disable)

                // 응답코드 변환 403 -> 401
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )

                // HttpServletRequest를 사용하는 요청들에 대한 접근제한 설정
                .authorizeHttpRequests(authorizeHttpRequests ->
                                authorizeHttpRequests

                                        // 인증된 사용자에게만 접근 허용
                                        .requestMatchers(
                                                new AntPathRequestMatcher("/members/info"),
                                                new AntPathRequestMatcher("/games/create"),
                                                new AntPathRequestMatcher("/games/{id}/report"),
                                                new AntPathRequestMatcher("/games/{id}/comment"),
                                                new AntPathRequestMatcher("/comment/**"),
                                                new AntPathRequestMatcher("/games/{gameId}/vote")
                                        ).authenticated()

                                        // 나머지 요청은 인증없이 접근을 허용
                                        .anyRequest().permitAll()
                )

                // 세션을 사용하지 않기 때문에 STATELESS로 설정
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }
}