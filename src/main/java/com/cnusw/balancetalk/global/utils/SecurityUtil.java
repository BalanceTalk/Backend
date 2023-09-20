package com.cnusw.balancetalk.global.utils;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecurityUtil {

    private SecurityUtil() {}

    /**
     * SecurityContext에서 인증 정보를 꺼내온 후 그 정보에 담겨 있는 이메일을 반환한다.
     */
    public static Optional<String> getCurrentEmail() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            log.debug("Security Context에 인증 정보가 없습니다.");
            return Optional.empty();
        }

        if (authentication.getPrincipal() instanceof UserDetails) {
            return Optional.ofNullable((
                    (UserDetails) authentication.getPrincipal()
            ).getUsername());
        } else if (authentication.getPrincipal() instanceof String) {
            return Optional.ofNullable((String) authentication.getPrincipal());
        }

        return Optional.empty();
    }
}
