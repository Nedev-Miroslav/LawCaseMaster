package com.example.lawcasemaster.config;

import com.example.lawcasemaster.model.enums.RoleType;
import com.example.lawcasemaster.repository.UserRepository;
import com.example.lawcasemaster.service.LawCaseMasterUserDetailsService;
import com.example.lawcasemaster.service.impl.LawCaseMasterUserDetailsServiceImpl;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .authorizeHttpRequests(
                        authorizeRequests ->
                                authorizeRequests
                                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                        .requestMatchers("/", "/users/login", "/users/register", "/users/login-error", "/about").permitAll()
                                        .requestMatchers("/api/**").permitAll()
                                        .requestMatchers("/admin").hasRole(RoleType.ADMIN.name())
                                        .anyRequest()
                                        .authenticated()

                )
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                )
                .cors(cors -> {
            cors.configurationSource(request -> {
                var corsConfiguration = new org.springframework.web.cors.CorsConfiguration();
                corsConfiguration.setAllowedOrigins(List.of("http://localhost:8080"));
                corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                corsConfiguration.setAllowCredentials(true);
                corsConfiguration.setAllowedHeaders(List.of("*"));
                return corsConfiguration;
            });
        })
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/users/login")
                                .usernameParameter("username")
                                .passwordParameter("password")
                                .defaultSuccessUrl("/", true)
                                .failureUrl("/users/login-error")

                )
                .logout(
                        logout ->
                                logout

                                        .logoutUrl("/users/logout")
                                        .logoutSuccessUrl("/")
                                        .invalidateHttpSession(true)
                )
                .build();
    }

    @Bean
    public LawCaseMasterUserDetailsService userDetailsService(UserRepository userRepository) {
        return new LawCaseMasterUserDetailsServiceImpl(userRepository);
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return Pbkdf2PasswordEncoder
                .defaultsForSpringSecurity_v5_8();
    }
}
