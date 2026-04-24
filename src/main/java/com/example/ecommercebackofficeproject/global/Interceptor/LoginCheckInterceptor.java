package com.example.ecommercebackofficeproject.global.Interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 로그인 여부를 검증하는 인터셉터입니다.
 * 모든 요청이 컨트롤러에 도달하기 전, 세션 존재 여부와
 * 세션 내 로그인 사용자 정보(loginUser)를 확인하여 인가되지 않은 접근을 차단합니다.
 */
public class LoginCheckInterceptor implements HandlerInterceptor {

    /**
     * 컨트롤러 실행 전 로그인 상태를 확인합니다.
     *
     * @param request  현재 HTTP 요청 객체
     * @param response 현재 HTTP 응답 객체
     * @param handler  실행될 핸들러(컨트롤러) 정보
     * @return 로그인 상태인 경우 true, 그렇지 않으면 false를 반환하여 요청을 중단함
     * @throws Exception 응답 작성 중 발생할 수 있는 예외
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 세션이 있는지 확인 (없으면 null 반환)
        HttpSession session = request.getSession(false);

        // 세션이 없거나, 세션 안에 "admin" 데이터가 없으면 401 에러를 던지고 끝냄
        if (session == null || session.getAttribute("loginUser") == null) {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized

            response.setContentType("application/json;charset=UTF-8");

            response.getWriter().write("{\"message\": \"로그인이 필요합니다.\"}");

            return false; // 컨트롤러로 못 가게 막음
        }

        return true; // 로그인 되어 있으면 통과
    }

}
