package io.github.guilherme_s_barros.auth_spring_api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import io.github.guilherme_s_barros.auth_spring_api.domain.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
  @Value("${api.security.token.secret}") private String secret;

  public String generateToken(User user) {
    try {
      var algorithm = Algorithm.HMAC256(secret);

      return JWT
        .create()
        .withIssuer("auth-spring-api")
        .withSubject(user.getEmail())
        .withExpiresAt(generateExpirationDate())
        .sign(algorithm);
    } catch (JWTCreationException exception) {
      throw new RuntimeException("Error while authenticating.");
    } catch (IllegalArgumentException exception) {
      throw new RuntimeException("Secret not valid.");
    }
  }

  public String validateToken(String token) {
    try {
      var algorithm = Algorithm.HMAC256(secret);

      return JWT
        .require(algorithm)
        .withIssuer("auth-spring-api")
        .build()
        .verify(token)
        .getSubject();
    } catch (JWTVerificationException exception) {
      return null;
    }
  }

  private Instant generateExpirationDate() {
    return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-3"));
  }
}
