package com.greta.masa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class MiddleWare {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().disable() // CORS 방지
                .csrf().disable() // CSRF 방지
                .formLogin().disable() // 기본 로그인 페이지 비활성화
                .headers().frameOptions().disable() // X-Frame-Options 비활성화
                .and()
                .logout()
                .logoutSuccessUrl("/") // 로그아웃 후에 홈페이지로 이동
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .and()
                .exceptionHandling() // 예외 처리 설정
                .authenticationEntryPoint((request, response, authException) -> response.sendRedirect("/login?error=true")) // 인증 예외 처리
                .accessDeniedPage("/403"); // 접근 거부 예외 처리

        return http.build();
    }
}
