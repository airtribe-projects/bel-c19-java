package org.airtribe.AuthenticationAuthorizationC19.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.airtribe.AuthenticationAuthorizationC19.entity.User;


public class JwtUtil {

  public static String generateJWTtoken(User user) {
    return Jwts.builder().subject(user.getUsername())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + 8 * 60 * 60 * 1000))
        .claim("roles", "ROLE_" + user.getRole())
        .claim("dummyField", "test")
        .claim("emailVerified", user.isEnabled())
        .signWith(SignatureAlgorithm.HS256, "airtribeC19AuthenticationAuthorizationairtribeC19AuthenticationAuthorizationairtribeC19AuthenticationAuthorizationairtribeC19AuthenticationAuthorizationairtribeC19AuthenticationAuthorization")
        .compact();

  }

  public static Claims validateJwtToken(String token) {
    Claims claims = Jwts.parser().setSigningKey("airtribeC19AuthenticationAuthorizationairtribeC19AuthenticationAuthorizationairtribeC19AuthenticationAuthorizationairtribeC19AuthenticationAuthorizationairtribeC19AuthenticationAuthorization")
    .build().parseClaimsJws(token).getBody();

    return claims;
  }
}
