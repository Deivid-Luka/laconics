package com.laconics.schoolService.securityConfig;

import com.laconics.schoolService.DTO.Authentification.JWTValues;
import com.laconics.schoolService.securityConfig.filter.Constants.SecurityConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTTokenFunctions {
    public String generateToken(Authentication user) {


        SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
        String role = user.getAuthorities().iterator().next().getAuthority();

        return Jwts.builder().setIssuer("laconics").setSubject("JWT Token").claim("username", user.getName()).claim("role", role).setIssuedAt(new Date()).setExpiration(new Date((new Date().getTime() + 30000000))).signWith(key).compact();

    }

    public JWTValues tokenValueExtractor(String jwt) {
        if (null != jwt) {
            try {
                SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));

                Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
                JWTValues jwtValues = new JWTValues();

                jwtValues.setUsername(String.valueOf(claims.get("username")));
                jwtValues.setRole(String.valueOf(claims.get("role")));
                return jwtValues;
            } catch (Exception e) {
                throw new BadCredentialsException("Invalid Token received!");

            }

        } else {
            throw new NullPointerException("No Token received!");
        }
    }

}
