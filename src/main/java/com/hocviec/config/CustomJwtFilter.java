package com.hocviec.config;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



public class CustomJwtFilter extends OncePerRequestFilter{

    private final String SIGNER_KEY;

    public CustomJwtFilter(String signerKey) {
        this.SIGNER_KEY = signerKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        try {


            SignedJWT signedJWT = SignedJWT.parse(token);
            MACVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

            boolean isVerified = signedJWT.verify(verifier);

            JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
            boolean isExpired = claimsSet.getExpirationTime().before(new Date());

            if (isVerified && !isExpired) {

                String username = claimsSet.getSubject();

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    username, null, Collections.emptyList());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {

            logger.error("Xác thực JWT thất bại: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }


}