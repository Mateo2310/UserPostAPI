package com.challenge.userpostapi.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String SECRET = "1f00b2d64bcf30deb2c59140b75932bff3ab0856eb30d0cd1fddd1b03dd3740c";
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

    public String extractUsername(String token) {
        return this.extractClaim(token, Claims::getSubject);
    }

    public String generateToken(UserDetails userDetails) {
        return this.generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> claims,
            UserDetails userDetails) {
        long now = System.currentTimeMillis();
        long expiration = now + 1000 * 60 * 60; // 1 hora
        return Jwts
                .builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(now))
                .expiration(new Date(expiration))
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = this.extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !this.isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return this.extracExpiration(token).before(new Date());
    }

    private Date extracExpiration(String token) {
        return this.extractClaim(token, Claims::getExpiration);
    }

    public<T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = this.extractClaimsJWT(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractClaimsJWT(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
