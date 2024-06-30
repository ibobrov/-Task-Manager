package ru.ibobrov.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class TokenService {
    private final Key secretKey;
    public final Long tokenExpiration;

    public TokenService(@Value("${jwt.key}") String key,
                        @Value("${jwt.access-token-expiration}") Long tokenExpiration) {
        secretKey = Keys.hmacShaKeyFor(key.getBytes());
        this.tokenExpiration = tokenExpiration;
    }

    @NotNull
    public String generateToken(@NotNull String username) {
        return Jwts
                .builder()
                .claims()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .add(Map.of())
                .and()
                .signWith(secretKey)
                .compact();
    }

    @NotNull
    public String extractUsername(@NotNull String token) {
        return getAllClaims(token).getSubject();
    }

    public boolean isValid(@NotNull String token, @NotNull String username) {
        final Claims claims = getAllClaims(token);
        final String login = claims.getSubject();
        final Date expiration = claims.getExpiration();

        return username.equals(login) && !expiration.before(new Date(System.currentTimeMillis()));
    }

    @NotNull
    private Claims getAllClaims(@NotNull String token) {
        return Jwts
                .parser()
                .verifyWith((SecretKey) secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
