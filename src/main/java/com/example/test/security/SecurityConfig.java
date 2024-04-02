package com.example.test.security;

import com.example.test.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests()
                .requestMatchers("/api/login", "/api/token/refresh").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/user/**").hasAnyAuthority("ROLE_USER")
                .requestMatchers(HttpMethod.POST, "/api/user/save/**").hasAnyAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/user/create/**").hasAnyAuthority("ROLE_SUPER_ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/user/block/**").hasAnyAuthority("ROLE_SUPER_ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/user/unlock/**").hasAnyAuthority("ROLE_SUPER_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/user/delete/**").hasAnyAuthority("ROLE_SUPER_ADMIN")
//                .requestMatchers("/api/**").access("@userService.isUserActive(principal)")
                .anyRequest().authenticated();
        http.apply(CustomSecurityDetails.customDsl());
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }




}