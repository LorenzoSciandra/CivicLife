package com.civiclife.oauthservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // definire il Bean per la configurazione della sicurezza
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http    .csrf().disable();
        http    .formLogin(form -> form.loginPage("/login").permitAll())
                .oauth2Login(customizer -> customizer.loginPage("/login"))
                .logout().permitAll();;
        http    .authorizeRequests((requests) -> requests
                    .requestMatchers("/authAPI/v1/validate/**", "/login", "/resources/**").permitAll()
                    .anyRequest().authenticated()
        );
        //http.oauth2Login(withDefaults());
        return http.build();
    }
}
