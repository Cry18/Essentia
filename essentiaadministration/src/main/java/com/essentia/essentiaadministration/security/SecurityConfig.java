package com.essentia.essentiaadministration.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security configuration for the essentiaadministration module.
 *
 * Rules:
 *  - all /api/admin/** endpoints → requires ROLE_ADMIN
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // Swagger UI — publicly accessible for API documentation
                .requestMatchers(
                    "/swagger-ui.html",
                    "/swagger-ui/**",
                    "/v3/api-docs",
                    "/v3/api-docs/**"
                ).permitAll()
                // Immagini caricate — servite pubblicamente (usate da catalog, FE non autenticato)
                .requestMatchers("/api/admin/uploads/**").permitAll()
                .anyRequest().hasAuthority("ROLE_ADMIN")
            )
            .exceptionHandling(ex -> ex
                // Spring Security intercepts 401/403 before reaching GlobalExceptionHandler.
                // These handlers ensure a JSON body is always returned.
                .authenticationEntryPoint((request, response, e) -> {
                    response.setContentType("application/json;charset=UTF-8");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("""
                        {"status":401,"error":"Unauthorized","message":"Invalid or missing JWT token. Please login first.","path":"%s"}
                        """.formatted(request.getRequestURI()).strip());
                })
                .accessDeniedHandler((request, response, e) -> {
                    response.setContentType("application/json;charset=UTF-8");
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("""
                        {"status":403,"error":"Forbidden","message":"This operation requires ROLE_ADMIN.","path":"%s"}
                        """.formatted(request.getRequestURI()).strip());
                })
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
