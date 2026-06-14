package org.airtribe.AuthenticationAuthorizationC19.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.airtribe.AuthenticationAuthorizationC19.util.JwtUtil;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


// if the token is available for the request
// if the token is valid or not
// if the token is not expired
// then let the request go through the filter chain, otherwise return 401 unauthorized response
@Component
@Order(2)
public class JwtFilter extends OncePerRequestFilter {
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String token = request.getHeader("Authorization");

    if (token == null || token.isEmpty() || token.isBlank()) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write("Missing Authorization header");
      return;
    }

    try {
      Claims generatedClaims = JwtUtil.validateJwtToken(token);
      String role = generatedClaims.get("roles", String.class);
      List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

      SecurityContextHolder.getContext().setAuthentication(
          new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(generatedClaims.getSubject(), null, authorities)
      );
    } catch (io.jsonwebtoken.ExpiredJwtException exception) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write("JWT token is expired: " + exception.getMessage());
      return;
    } catch (io.jsonwebtoken.SignatureException exception) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write("Invalid JWT signature: " + exception.getMessage());
      return;
    } catch (io.jsonwebtoken.MalformedJwtException exception) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write("Invalid JWT token: " + exception.getMessage());
      return;
    } catch (io.jsonwebtoken.UnsupportedJwtException exception) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write("JWT token is unsupported: " + exception.getMessage());
      return;
    } catch (IllegalArgumentException exception) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write("JWT claims string is empty: " + exception.getMessage());
      return;
    }

    filterChain.doFilter(request, response);


  }

  public boolean shouldNotFilter(HttpServletRequest request) {
    return request.getRequestURI().equals("/register") || request.getRequestURI().equals("/signin") || request.getRequestURI().equals("/verifyRegistrationToken");
  }
}
