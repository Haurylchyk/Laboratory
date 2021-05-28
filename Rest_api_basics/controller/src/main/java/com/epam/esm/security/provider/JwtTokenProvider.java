package com.epam.esm.security.provider;

import com.epam.esm.constant.ErrorCodeMessage;
import com.epam.esm.security.exception.JwtAuthException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.validity_time}")
    private int validityTime;

    @Value("${jwt.secret}")
    private String secret;

    public static final String AUTH = "Authorization";
    public static final String BEARER = "Bearer_";

    public String createToken(String login) {
        Date now = new Date();
        Date validityDate = new Date(now.getTime() + validityTime);
        return Jwts.builder()
                .setSubject(login)
                .setExpiration(validityDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthException(ErrorCodeMessage.ERROR_CODE_TOKEN_NOT_VALID);
        }
    }

    public String getLoginFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader(AUTH);
        if (bearer != null && bearer.startsWith(BEARER)) {
            return bearer.substring(7);
        }
        return null;
    }
}
