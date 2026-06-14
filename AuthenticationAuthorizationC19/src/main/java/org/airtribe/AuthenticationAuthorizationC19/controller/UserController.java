package org.airtribe.AuthenticationAuthorizationC19.controller;

import java.util.UUID;
import org.airtribe.AuthenticationAuthorizationC19.entity.User;
import org.airtribe.AuthenticationAuthorizationC19.service.AuthenticationAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

  @Autowired
  private AuthenticationAuthorizationService _authenticationAuthorizationService;

  @PostMapping("/register")
  public User registerUser(@RequestBody User user) throws Exception {
    User persistedUser = _authenticationAuthorizationService.registerUser(user);
    String token = UUID.randomUUID().toString();
    String verificationUrl = "http://localhost:4000/verifyRegistrationToken?token=" + token;
    System.out.println("Verification Url " + verificationUrl);;
     _authenticationAuthorizationService.saveVerificationToken(token, persistedUser);
     return persistedUser;
  }

  @PostMapping("/verifyRegistrationToken")
  public String verifyRegistration(@RequestParam("token") String verificationToken) {
    return _authenticationAuthorizationService.verifyTokenAndEnableUser(verificationToken);
  }

  @PostMapping("/signin")
  public String signinUser(@RequestParam("username") String username, @RequestParam("password") String password) {
    return _authenticationAuthorizationService.signinUser(username, password);
  }

  @GetMapping("/hello")
  @PreAuthorize("hasAnyRole('admin')")
  public String hello() {
    System.out.println("Thread handling /hello request: " + Thread.currentThread().getName());
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    System.out.println("Authenticated user: " + authentication.getName());
    return "Hello world";
  }

}
