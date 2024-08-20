package com.beautynoon.admin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebSecurityConfig implements WebMvcConfigurer {

    @Bean
    public PasswordEncoder passwordEncoders() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(customizer ->
                customizer
                        .anyRequest().authenticated())
                        .formLogin(form -> form.loginPage("/login").loginProcessingUrl("/process-login").permitAll());

        http.csrf(csrf -> csrf.disable());
        return http.build();
    }

    @Bean
    WebSecurityCustomizer customizeWebSecurity() {
        return (web) -> web.ignoring().requestMatchers("/images/**", "/js/**", "/css/**", "/webjars/**");
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(customizer ->
//                        customizer
//                                .anyRequest().permitAll());
//
//
//        http.csrf(csrf -> csrf.disable());
//        return http.build();
//    }


//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(customizer ->
//                        customizer
//                                .requestMatchers(HttpMethod.GET, "/").hasRole("EMPLOYEE")
//                                .requestMatchers(HttpMethod.GET, "/leaders/**").hasRole("MANAGER")
//                                .requestMatchers(HttpMethod.GET, "/bosses/**").hasRole("ADMIN")
//                                .anyRequest().authenticated())
//                .formLogin(form -> form.loginPage("/login").loginProcessingUrl("/process-login").permitAll())
//                .logout(logout -> logout.permitAll())
//                .exceptionHandling(customizer ->
//                        customizer.accessDeniedPage("/unauthorized")
//                );
//
//        return http.build();
//    }
}
