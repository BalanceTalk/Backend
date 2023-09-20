package com.cnusw.balancetalk.global.jwt;

import java.security.Key;

import java.util.Date;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtUtil implements InitializingBean {

    private final String secret;
    private final long tokenExpiredMs;
    private Key key;

    public JwtUtil(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.token-expired-seconds}") long tokenExpiredSeconds) {
        this.secret = secret;
        this.tokenExpiredMs = tokenExpiredSeconds * 1000; // 밀리초 단위로 계산되기 때문에 1000을 곱한다.
    }

    /**
     * 빈이 생성되면 주입받은 secret 값을 Base64로 디코딩해서 key 변수에 할당한다.
     */
    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 이메일 정보를 이용해서 토큰을 생성한다.
     */
    public String createToken(String email) {
        long now = new Date().getTime();

        Date tokenExpiredTime = new Date(now + this.tokenExpiredMs);

        return Jwts.builder()
                .setSubject(email) // 토큰 제목 등록
                .setExpiration(tokenExpiredTime) // 토큰 만료시간 설정
                .signWith(key, SignatureAlgorithm.HS512) // 키를 HS512 알고리즘으로 해싱하여 서명
                .compact(); // 압축하여 토큰 생성
    }

    /**
     * 토큰에서 이메일을 추출한다.
     */
    public String extractSubject(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // 토큰의 유효성 검사 (보류)
    private boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.error("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}
