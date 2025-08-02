package com.mh.cloud_storage_backend.model.util;
import com.mh.cloud_storage_backend.model.entities.Users;
import com.mh.cloud_storage_backend.model.repository.UsersRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

@Data
@Component
public class JWTUtil {

    @Value("${my.secret.key}")
    private String secretKey;
    private final long expirationTime = 1000 * 60 * 60; // 1 hour in milliseconds
    private String secret = secretKey;

    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @Autowired
    private final UsersRepo userRepository;
    public JWTUtil(UsersRepo userRepository) {
        this.userRepository = userRepository;
    }


    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(String username) {
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(signatureAlgorithm, getSigningKey())
                .compact();
        return token;
    }

    public String extractUsername(String token) {
        Claims body = Jwts.parserBuilder().
                setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return body.getSubject();
    }


    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Boolean validateToken(String token) {
        try {
            String username = extractUsername(token);
            Optional<Users> user = userRepository.findByEmail(username);
            if (user.isEmpty()) {
                return false;
            }
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String getTokenFromHeader(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    public String extractUsernameByRequest(HttpServletRequest request) {
        String token = getTokenFromHeader(request.getHeader("Authorization"));
        if (token != null && validateToken(token)) {
            return extractUsername(token);
        }
        return null;
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}