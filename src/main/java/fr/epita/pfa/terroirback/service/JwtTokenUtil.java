package fr.epita.pfa.terroirback.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

@Service
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secret;

    public  Object getClaimFromToken(String token, String field) {
        final Claims claims = getAllClaimsFromToken(token);
        return claims.get(field);
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getExpiration();
        return expiration.before(new Date());
    }

    public Boolean validateToken(String token) {
        return (!isTokenExpired(token));
    }
}
