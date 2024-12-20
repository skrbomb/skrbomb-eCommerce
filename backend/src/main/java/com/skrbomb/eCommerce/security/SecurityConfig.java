package com.skrbomb.eCommerce.security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    /*Customizer.withDefaults() 是一個開關，讓 Spring Security 使用 Spring MVC 配置的 CORS。
    * WebMvcConfigurer 是用來定義具體 CORS 規則的地方(ex:CorsConfig)。*/
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(request->request
                        .requestMatchers("/auth/**","/category/**","/product/**","/order/**").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(manager->manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /*這個 authenticationManager 方法的作用是：
    1. 註冊一個 AuthenticationManager Bean，供其他元件使用。
    2. 利用 AuthenticationConfiguration，簡化 AuthenticationManager 的初始化流程。
    3. 提供身份驗證的核心邏輯，並支援多種驗證方式（如使用者密碼驗證、JWT Token 驗證等）。
      這樣的設計使得身份驗證邏輯更加靈活且易於擴展，非常適合用於需要多種身份驗證機制的應用場景。

    在 JWT 驗證場景中，AuthenticationManager 通常會配合 UserDetailsService 來完成用戶驗證：
    1. 使用者發送憑證（如登入時提交的用戶名稱和密碼）。
    2. AuthenticationManager 接收憑證並交給 UserDetailsService 查詢用戶資訊。
    3. 若密碼匹配（透過 PasswordEncoder 比對），驗證通過。
    4. 若使用者提供的 Token（如 JWT）已過期或無效，則驗證失敗。*/
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
