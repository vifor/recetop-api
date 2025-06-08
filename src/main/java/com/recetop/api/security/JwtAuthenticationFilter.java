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

        // 1. Get the Authorization header from the incoming request.
        final String authHeader = request.getHeader("Authorization");

        // 2. Check if the header is null or doesn't start with "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // If it's not a valid Bearer token, pass the request to the next filter and exit.
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Extract the token from the header (remove "Bearer ").
        final String jwt = authHeader.substring(7);

        // 4. Validate the token. For now, we just check if it matches our hardcoded token.
        if (jwt.equals(HARDCODED_TOKEN)) {
            // If the token is valid, we need to tell Spring Security that the user is authenticated.

            // We create a dummy UserDetails object since we don't have a user database yet.
            UserDetails userDetails = new User("hardcoded-user", "", Collections.emptyList());

            // Create an authentication token.
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );

            // Set the authentication token in the SecurityContext.
            // This is the crucial step that marks the current user as authenticated.
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        // 5. Pass the request and response to the next filter in the chain.
        filterChain.doFilter(request, response);
    }
}