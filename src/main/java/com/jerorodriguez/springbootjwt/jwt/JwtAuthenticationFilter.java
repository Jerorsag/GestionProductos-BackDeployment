package com.jerorodriguez.springbootjwt.jwt;

import com.jerorodriguez.springbootjwt.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Log para depuración
        logger.info("Processing request: {} {}", request.getMethod(), request.getRequestURI());

        // Comprobar si es una solicitud OPTIONS (preflight)
        if (request.getMethod().equals("OPTIONS")) {
            logger.info("Preflight request detected, skipping JWT authentication");
            filterChain.doFilter(request, response);
            return;
        }

        // Obtener el token de la petición
        final String token = getTokenFromRequest(request);
        if (token == null) {
            logger.info("No token found in request");
            filterChain.doFilter(request, response);
            return;
        }

        logger.info("JWT token found in request");

        try {
            // Extraer el username del token
            String username = jwtService.extractUsername(token);
            logger.info("Username extracted from token: {}", username);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Cargar usuario desde la BD
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                logger.info("User details loaded for username: {}", username);

                // Validar el token
                if (jwtService.isTokenValid(token, userDetails)) {
                    logger.info("Token is valid for user: {}", username);
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Establecer autenticación en el contexto de seguridad
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    logger.info("Authentication set in SecurityContext for user: {}", username);
                } else {
                    logger.warn("Token validation failed for user: {}", username);
                }
            }
        } catch (Exception e) {
            logger.error("Error processing JWT token", e);
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        logger.info("Authorization header: {}", authHeader);

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}