package kr.co.imguru.global.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .cors().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //authorization
        http
                .authorizeHttpRequests()
//                .requestMatchers("/api/v1/login", "api/v1/refresh").permitAll()
//                .requestMatchers("/api/v1/member").permitAll()
//                .requestMatchers("/api/v1/skill/**").hasAnyRole("USER", "GURU", "ADMIN", "MANAGER")
//                .requestMatchers("/api/v1/post/**").hasAnyRole("USER", "GURU", "ADMIN", "MANAGER")
//                .requestMatchers("/api/v1/reply/**").hasAnyRole("USER", "GURU", "ADMIN", "MANAGER")
//                .requestMatchers("/api/v1/message/**").hasAnyRole("USER", "GURU", "ADMIN", "MANAGER")
//                .requestMatchers("/api/v1/review/**").hasAnyRole("USER", "GURU", "ADMIN", "MANAGER")
//                .requestMatchers("/api/v1/report/**").hasAnyRole("USER", "GURU", "ADMIN", "MANAGER")
//                .requestMatchers("/api/v1/admin/**").hasAnyRole("ADMIN")
                .requestMatchers("/**").permitAll()
                .anyRequest().authenticated();

//        //authentication
//        http
//                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
//                .exceptionHandling()
//                // 권한 없을 때 오류 발생 - 요청한 경로의 USER_ROLE이 적합하지 않을 때
//                .accessDeniedHandler(new AccessDeniedHandler() {
//                    @Override
//                    public void handle(HttpServletRequest request, HttpServletResponse response,
//                                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
//                        response.setStatus(ResponseStatus.FAIL_FORBIDDEN.getStatusCode().value());
//                        response.getWriter().write(ResponseStatus.FAIL_FORBIDDEN.getMessage());
//                    }
//                })
//                // 인증이 올바르지 않을 때 오류 발생 - token 오류
//                .authenticationEntryPoint(new AuthenticationEntryPoint() {
//                    @Override
//                    public void commence(HttpServletRequest request, HttpServletResponse response,
//                                         AuthenticationException authException) throws IOException, ServletException {
//                        response.setStatus(ResponseStatus.FAIL_UNAUTHORIZED.getStatusCode().value());
//                        response.getWriter().write(ResponseStatus.FAIL_UNAUTHORIZED.getMessage());
//                    }
//                });
//
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
