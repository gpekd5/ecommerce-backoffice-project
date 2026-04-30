package com.example.ecommercebackofficeproject.auth.jwt;

import com.example.ecommercebackofficeproject.auth.dto.SessionAdminDto;
import com.example.ecommercebackofficeproject.global.exception.*;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class JwtFilter implements HandlerInterceptor {

    private final JwtProvider jwtProvider;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }

        if (!authorizationHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("토큰 형식이 올바르지 않습니다.");
        }

        String token = authorizationHeader.substring(7);

        Claims claims = jwtProvider.getClaims(token);

        request.setAttribute("adminId", Long.valueOf(claims.getSubject()));
        request.setAttribute("adminEmail", claims.get("email", String.class));
        request.setAttribute("adminRole", claims.get("role", String.class));

        SessionAdminDto sessionAdmin = new SessionAdminDto(
                Long.valueOf(claims.getSubject()),
                null,
                claims.get("email", String.class),
                claims.get("role", String.class)
        );

        request.setAttribute("loginUser", sessionAdmin);

        return true;
    }

}
