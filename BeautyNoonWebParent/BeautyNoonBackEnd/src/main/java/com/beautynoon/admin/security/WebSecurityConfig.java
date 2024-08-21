package com.beautynoon.admin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebSecurityConfig {
    private UserDetailsService userDetailsService;

    @Autowired
    public WebSecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new BeautyNoonUserDetailsService();
//    }

    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authenticationProvider(authenticationProvider());

        http.authorizeHttpRequests(customizer ->
                customizer
                        .anyRequest().authenticated())
                        .formLogin(form -> form
                                .loginPage("/login")
                                .usernameParameter("email")
                                .defaultSuccessUrl("/", true)
                                .permitAll())
                .logout(logout -> logout.permitAll())
                .rememberMe(rem -> rem
                        .key("AbcDefgHijKlmnOpqrs_1234567890")
                        .tokenValiditySeconds(7 * 24 * 60 * 60));

        http.csrf(csrf -> csrf.disable());
        return http.build();
    }

    @Bean
    WebSecurityCustomizer customizeWebSecurity() {
        return (web) -> web.ignoring().requestMatchers("/images/**", "/js/**", "/css/**", "/webjars/**", "/bootstrap-icons/**");
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
