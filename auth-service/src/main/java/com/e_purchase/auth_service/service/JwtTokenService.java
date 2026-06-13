package com.e_purchase.auth_service.service;

import com.e_purchase.auth_service.Entity.UserInfo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtTokenService {

    private static final String CLAIM_USER_ID = "user_id";
    private static final String CLAIM_ROLE = "role";
    private static final String ISSUER = "auth-service";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expirationTime;

    public String generateAuthenticationToken(UserInfo userInfo) {
        return Jwts.builder()
                .setSubject(userInfo.getUserName())
                .claim(CLAIM_USER_ID, userInfo.getId())
                .claim(CLAIM_ROLE, userInfo.getUserType())
                .setIssuer(ISSUER)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }
}
