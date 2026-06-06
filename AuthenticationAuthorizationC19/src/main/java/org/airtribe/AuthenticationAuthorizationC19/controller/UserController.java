package org.airtribe.AuthenticationAuthorizationC19.controller;

import org.airtribe.AuthenticationAuthorizationC19.entity.User;
import org.airtribe.AuthenticationAuthorizationC19.service.AuthenticationAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

  @Autowired
  private AuthenticationAuthorizationService _authenticationAuthorizationService;

  @PostMapping("/register")
  public User registerUser(@RequestBody User user) throws Exception {
    return _authenticationAuthorizationService.registerUser(user);
  }
}
