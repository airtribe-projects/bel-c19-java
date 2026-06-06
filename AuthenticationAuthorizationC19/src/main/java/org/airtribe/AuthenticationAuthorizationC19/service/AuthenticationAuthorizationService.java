package org.airtribe.AuthenticationAuthorizationC19.service;

import java.util.Optional;
import org.airtribe.AuthenticationAuthorizationC19.entity.User;
import org.airtribe.AuthenticationAuthorizationC19.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationAuthorizationService {

  @Autowired
  private UserRepository _userRepository;

  @Autowired
  private BCryptPasswordEncoder _passwordEncoder;

  public User registerUser(User user) throws Exception {
    Optional<User> existingUser = _userRepository.findByEmail(user.getEmail());
    if (existingUser.isPresent()) {
      throw new Exception("User with email " + user.getEmail() + " already exists.");
    }
    String hashedPassword = _passwordEncoder.encode(user.getPassword());
    user.setPassword(hashedPassword);
    return _userRepository.save(user);
  }
}
