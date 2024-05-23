package com.se.leopard.serviceauthentication.security;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.se.leopard.serviceauthentication.enumric.RoleName;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtUltilities {
	
	private static final Logger logger = LoggerFactory.getLogger(JwtUltilities.class);
	
	@Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}
    
	public Boolean validateToken(String token) {
		try {
	      Jwts.parserBuilder().setSigningKey(key()).build().parse(token);
	      return true;
	    } catch (MalformedJwtException e) {
	      logger.error("Invalid JWT token: {}", e.getMessage());
	      e.printStackTrace();
	    } catch (ExpiredJwtException e) {
	      logger.error("JWT token is expired: {}", e.getMessage());
	      e.printStackTrace();
	    } catch (UnsupportedJwtException e) {
	      logger.error("JWT token is unsupported: {}", e.getMessage());
	      e.printStackTrace();
	    } catch (IllegalArgumentException e) {
	      logger.error("JWT claims string is empty: {}", e.getMessage());
	      e.printStackTrace();
	    }

	    return false;
    }

    public String generateToken(String username , RoleName roleName) {
    	System.out.println("username=" + username);
        return Jwts.builder()
        		.setSubject(username)
        		.claim("role",roleName)
        		.setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(Date.from(Instant.now().plus(jwtExpiration, ChronoUnit.MILLIS)))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }
    
    public String getMssvFromJwtToken(String token) {
        return Jwts.parserBuilder()
        		.setSigningKey(key())
        		.build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
      }

    public String getToken(HttpServletRequest httpServletRequest) {
         final String bearerToken = httpServletRequest.getHeader("Authorization");
         if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
        	 return bearerToken.substring(7,bearerToken.length());
         } // The part after "Bearer "
         return null;
    }
}