package com.example.taho.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()  // ✅ すべてのページを認証なしでアクセス可能
            )
            .csrf().disable()  // ✅ CSRFを無効化（開発環境ならOK）
            .formLogin().disable() // ✅ ログインフォームを無効化
            .logout().disable();  // ✅ ログアウト機能も無効化

        return http.build();
    }
}
