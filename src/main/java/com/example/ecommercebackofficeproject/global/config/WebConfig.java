package com.example.ecommercebackofficeproject.global.config;

import com.example.ecommercebackofficeproject.global.Interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 애플리케이션의 웹 관련 설정을 담당하는 설정 클래스입니다.
 * WebMvcConfigurer를 구현하여 인터셉터 등록, 리소스 핸들러 설정 등
 * Spring MVC의 기능을 확장하거나 커스터마이징합니다.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 애플리케이션에 인터셉터를 등록합니다.
     * <p>
     * 로그인 체크 인터셉터를 등록하여 모든 요청에 대해 인증 여부를 검사하되,
     * 로그인 및 회원가입과 같은 예외 경로는 제외하도록 설정합니다.
     *
     * @param registry 인터셉터를 등록하고 관리하는 레지스트리
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1) // 우선순위
                .addPathPatterns("/**") // 모든 경로에 적용하되
                .excludePathPatterns("/admin/login", "/admin/signup"); // 로그인, 회원가입 등은 제외
    }
}
