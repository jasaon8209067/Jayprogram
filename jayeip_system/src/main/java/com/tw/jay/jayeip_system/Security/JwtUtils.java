package com.tw.jay.jayeip_system.Security;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils {
    private String secretKey = "JayEipSystemSecretKeyForJWTGeneration";
    private long expirationMs = 3600000; // token有效期限 1 小時

    //產生token
    public String generateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + expirationMs))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    } 
}
