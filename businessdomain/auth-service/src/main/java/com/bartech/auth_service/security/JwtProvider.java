package com.bartech.auth_service.security;

import com.bartech.auth_service.entity.AuthUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secret;

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(AuthUser authUser) {
        Map<String, Object> claims = new HashMap<>();
        claims = Jwts.claims().setSubject(authUser.getUserName());
        claims.put("id", authUser.getId());
        Date now = new Date();
        Date exp = new Date(now.getTime() + 259200000);
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(exp)
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact();
    }

    public boolean validate(String token) {
        try{
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUserNameFromToken(String token) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
        } catch (Exception e) {
            return "bad Token";
        }
    }
}
