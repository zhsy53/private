package com.pv.autoconfigure.token.service;

import com.pv.autoconfigure.PrivateProperties.Token.Jwt;
import com.pv.commons.exception.TokenException;
import com.pv.commons.util.TimeUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;

import static java.nio.charset.StandardCharsets.UTF_8;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class JwtTokenService implements TokenService<@NotNull String> {
    @NotNull
    private final Jwt jwt;

    @Override
    public @NotBlank String generateToken(@NotNull String data) {
        return Jwts.builder()
                .setSubject(String.valueOf(data))
                .setExpiration(TimeUtils.toDate(LocalDateTime.now().plusMinutes(jwt.getExpirationMinutes())))
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, this.signingKey())
                .compact();
    }

    @Override
    public @NotNull String extractToken(@NotBlank String token) {
        try {
            return Jwts.parser().setSigningKey(this.signingKey()).parseClaimsJws(token).getBody().getSubject();
        } catch (ExpiredJwtException e) {
            throw new TokenException("token已过期", e);
        } catch (Exception e) {
            throw new TokenException("token错误", e);
        }
    }

    private @NotNull byte[] signingKey() {
        return Base64.getEncoder().encode(jwt.getKey().getBytes(UTF_8));
    }
}
