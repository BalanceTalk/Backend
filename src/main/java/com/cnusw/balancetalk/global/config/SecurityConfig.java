package com.cnusw.balancetalk.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
                .csrf(csrf -> csrf.disable())

                // 응답코드 변환 403 -> 401
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )

                // HttpServletRequest를 사용하는 요청들에 대한 접근제한 설정
                .authorizeHttpRequests(authorizeHttpRequests ->
                                authorizeHttpRequests
                                        // 지정한 경로에 대한 요청은 인증없이 접근을 허용
//                                .requestMatchers("/members/login", "/members/join", "/members/info").permitAll()
                                        .requestMatchers(
                                                new AntPathRequestMatcher("/v3/api-docs/**"),
                                                new AntPathRequestMatcher("/swagger-ui/**"),
                                                new AntPathRequestMatcher("/swagger-resources/**"),
                                                new AntPathRequestMatcher("/members/login"),
                                                new AntPathRequestMatcher("/members/join"),
                                                new AntPathRequestMatcher("/members/info"),
                                                new AntPathRequestMatcher("/h2-console/**"),
                                                new AntPathRequestMatcher("/games/**")
                                        ).permitAll()
//                                .requestMatchers("/members/info").authenticated() // 인증된 사용자에게만 접근 허용
                                        .requestMatchers(new AntPathRequestMatcher("/members/info")).authenticated()
                                        // 나머지 요청은 모두 인증 필요
                                        .anyRequest().authenticated()

                )

                // 세션을 사용하지 않기 때문에 STATELESS로 설정
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }
}