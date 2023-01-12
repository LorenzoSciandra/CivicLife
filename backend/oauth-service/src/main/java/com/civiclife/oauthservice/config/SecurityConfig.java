package com.civiclife.oauthservice.config;

import com.civiclife.oauthservice.service.*;
import com.civiclife.oauthservice.utils.AES;
import lombok.AllArgsConstructor;
import org.apache.hc.core5.http.HttpStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.Base64;
import java.util.Objects;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private OAuth2UserService oAuth2UserService;

    private final String secret = "!CivicLifeSecret2023!";


    // definire il Bean per la configurazione della sicurezza
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors().and().csrf().disable()
                .formLogin(form -> form.loginPage("/login")
                            .permitAll()
                            .failureHandler(basicLoginFailureHandler())
                            .successHandler(basicLoginSuccessHandler()))
                .oauth2Login(customizer -> customizer
                                            .loginPage("/login")
                                            .successHandler(oauth2SuccessHandler()))
                /*.exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpStatus.SC_UNAUTHORIZED))
                .and()*/
                .authorizeRequests(requests -> requests
                                                .requestMatchers(  "/authAPI/**",
                                                                            "/resources/templates/**",
                                                                            "/login/**",
                                                                            "/login",
                                                                            "/oauth2/**",
                                                                            "/validate/**")
                                                .permitAll()
                                                .anyRequest()
                                                .authenticated())
                .build();
    }

    @Bean
    AuthenticationSuccessHandler basicLoginSuccessHandler() {
        return (request, response, authentication) -> {
            try {
                //userService.processUserLogin();
                response.setStatus(HttpStatus.SC_OK);
            } catch (ResponseStatusException ex) {
                //userService.logoutUser(request, response);
                throw ex;
            }
        };
    }

    @Bean
    AuthenticationFailureHandler basicLoginFailureHandler() {
        return (request, response, exception) -> {
            response.sendError(HttpStatus.SC_UNAUTHORIZED, exception.getMessage());
        };
    }


    @Bean
    AuthenticationSuccessHandler oauth2SuccessHandler() {
        return (request, response, authentication) -> {
            try {
                String tokenData = oAuth2UserService.processOauthPostLogin(
                        ((OAuth2AuthenticationToken) authentication)
                );

                String encryptedToken = AES.encrypt(tokenData, secret);
                String base64Token = Base64.getEncoder().encodeToString(Objects.requireNonNull(encryptedToken).getBytes());
                System.out.println("HO CIFRATO CAZZO: " + encryptedToken);
                System.out.println("HO CIFRATO IL CAZZO IN BASE64: " + base64Token);
                response.sendRedirect("http://localhost:3000/home?token=" + base64Token);
            } catch (ResponseStatusException ex) {
                response.sendRedirect("http://localhost:3000/error?errorReason="+ex.getReason());
            }
        };
    }
}
