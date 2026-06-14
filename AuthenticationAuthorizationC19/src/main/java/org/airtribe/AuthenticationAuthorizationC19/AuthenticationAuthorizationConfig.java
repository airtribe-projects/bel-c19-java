package org.airtribe.AuthenticationAuthorizationC19;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableMethodSecurity
public class AuthenticationAuthorizationConfig {
  @Bean
  public BCryptPasswordEncoder _passwordEncoder() {
    return new BCryptPasswordEncoder(11);
  }

  @Bean
  public SecurityFilterChain httpSecurityFilterChain(HttpSecurity httpSecurity) {
    try {
      httpSecurity.csrf(csrf -> csrf.disable())
          .authorizeHttpRequests(authorizeRequests -> authorizeRequests
              .requestMatchers("/register", "/verifyRegistrationToken", "/signin", "/hello")
              .permitAll()
              .anyRequest()
              .authenticated())
          .formLogin(formLogin -> formLogin.defaultSuccessUrl("/", true).permitAll());
      return httpSecurity.build();
    } catch (Exception e) {
      throw new RuntimeException("Error configuring security filter chain", e);
    }
  }
}

// Rounds of hashing

// hash(pawan) -> 1234
// hash(1234) -> 5678
// hash(5678) -> 91011
