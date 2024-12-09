package com.epam.gym.app.security;

import com.epam.gym.app.entity.Token;
import com.epam.gym.app.entity.User;
import com.epam.gym.app.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class JwtService {

    private static final String SECRET_KEY = "01d18531e10c1a1bd85130b93dcb449e72c71cdfe45e85b8d653585e8ccf90b3";
    private static final long TOKEN_EXP_TIME_SEC = 24 * 60 * 60 * 1000L;

    private final TokenRepository tokenRepository;

    public String generateToken(User user) {
        return Jwts
                .builder()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + TOKEN_EXP_TIME_SEC))
                .signWith(getSigningKey())
                .compact();
    }

    public boolean isValid(String tokenName, UserDetails user) {
        String username = extractUsername(tokenName);

        boolean isLoggedOut = tokenRepository.findByName(tokenName)
                .map(Token::isLoggedOut)
                .orElse(false);

        return (username.equals((user.getUsername()))
                && !isTokenExpired(tokenName)
                && !isLoggedOut);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }
}
