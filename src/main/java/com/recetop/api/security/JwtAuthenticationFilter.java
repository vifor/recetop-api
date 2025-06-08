package com.recetop.api.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component // Marks this as a Spring component to be managed by the application context
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // --- THIS IS OUR TEMPORARY, HARDCODED TOKEN ---
    // In a real application, you would validate this using a secret key.
    private static final String HARDCODED_TOKEN = "my-super-secret-jwt-token-for-testing";

   @Override
protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain) throws ServletException, IOException {

    final String authHeader = request.getHeader("Authorization");

    // If there is no token, let the request continue.
    // Spring Security will block it later if the endpoint is protected.
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        filterChain.doFilter(request, response);
        return;
    }

    // A Bearer token is present. We must validate it now.
    final String jwt = authHeader.substring(7);

    if (jwt.equals(HARDCODED_TOKEN)) {
        // --- TOKEN IS VALID ---
        // Create a dummy UserDetails object
        UserDetails userDetails = new User("hardcoded-user", "", Collections.emptyList());

        // Create an authentication token and set it in the context
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // Continue the filter chain
        filterChain.doFilter(request, response);
    } else {
        // --- TOKEN IS PRESENT BUT INVALID ---
        // Reject the request immediately.
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write("{\"error\": \"Invalid or Expired Token\"}");
        // IMPORTANT: We do NOT continue the filter chain here.
    }
}
}