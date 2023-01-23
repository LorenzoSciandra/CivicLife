package com.civiclife.oauthservice.config;

import com.civiclife.oauthservice.utils.AES;
import com.civiclife.oauthservice.service.OAuth2Service;
import com.civiclife.oauthservice.utils.ErrorMessage;
import com.civiclife.oauthservice.utils.ValidateCode;
import com.mongodb.lang.NonNull;
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
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Base64;
import java.util.Objects;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private OAuth2Service oAuth2UserService;

    private final String secret = "!CivicLifeSecret2023!";


    // Bean per la configurazione della sicurezza
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors().disable().csrf().disable()
                .formLogin(form -> form.loginPage("/login")
                            .permitAll()
                            .failureHandler(basicLoginFailureHandler())
                            .successHandler(basicLoginSuccessHandler()))
                .oauth2Login(customizer -> customizer
                                            .loginPage("/login")
                                            .successHandler(oauth2SuccessHandler()))
                .authorizeRequests(requests -> requests
                                                .requestMatchers(  "/authAPI/**",
                                                                            "/resources/templates/**",
                                                                            "/login/**",
                                                                            "/login",
                                                                            "/oauth2/**")
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

                String [] email = tokenData.split(",")[0].split(":");

                if(email.length == 2 && email[1].contains("@")) {
                    String encryptedToken = AES.encrypt(tokenData, secret);
                    String base64Token = Base64.getEncoder().encodeToString(Objects.requireNonNull(encryptedToken).getBytes());

                    response.sendRedirect("http://localhost:3000/home?token=" + base64Token);
                }
                else {
                    ErrorMessage errorMessage = new ErrorMessage(ValidateCode.AUTH_SERVER_ERROR, "http://localhost:8080/login","GET");
                    response.sendRedirect("http://localhost:3000/error?errorReason=" + errorMessage.toString());
                }
            } catch (ResponseStatusException ex) {
                ErrorMessage errorMessage = new ErrorMessage(ValidateCode.AUTH_SERVER_ERROR, "http://localhost:8080/login","GET");
                response.sendRedirect("http://localhost:3000/error?errorReason="+errorMessage.toString());
            }
        };
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowCredentials(true)
                        .allowedHeaders("Authorization", "Cache-Control", "Content-Type",
                                "Access-Control-Request-Headers", "Access-Control-Request-Method",
                                "Access-Control-Allow-Origin", "Access-Control-Allow-Headers")
                        .allowedOrigins("http://localhost:3000");
            }
        };
    }
}
