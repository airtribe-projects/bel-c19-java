package org.airtribe.AuthenticationAuthorizationC19.service;

import java.util.Date;
import java.util.Optional;
import org.airtribe.AuthenticationAuthorizationC19.entity.User;
import org.airtribe.AuthenticationAuthorizationC19.entity.VerificationToken;
import org.airtribe.AuthenticationAuthorizationC19.repository.UserRepository;
import org.airtribe.AuthenticationAuthorizationC19.repository.VerificationTokenRepository;
import org.airtribe.AuthenticationAuthorizationC19.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationAuthorizationService implements UserDetailsService {

  @Autowired
  private UserRepository _userRepository;

  @Autowired
  private VerificationTokenRepository _verificationTokenRepository;

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

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> user = _userRepository.findByUsername(username);
    if (!user.isPresent()) {
      throw new UsernameNotFoundException("User name " + user + " not found");
    }
    User fetchedUser = user.get();
    return org.springframework.security.core.userdetails.User.builder()
        .username(fetchedUser.getUsername())
        .password(fetchedUser.getPassword())
        .roles(fetchedUser.getRole())
        .disabled(!fetchedUser.isEnabled()).build();
  }

  public void saveVerificationToken(String token, User persistedUser) {
    VerificationToken verificationToken = new VerificationToken();
    verificationToken.setToken(token);
    verificationToken.setUser(persistedUser);
    verificationToken.setExpiryAt(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000));
    _verificationTokenRepository.save(verificationToken);
  }

  public String verifyTokenAndEnableUser(String verificationToken) {
    Optional<VerificationToken> registeredTokenOpt = _verificationTokenRepository.findByToken(verificationToken);
    if (!registeredTokenOpt.isPresent()) {
      return "Verification Failed, Incorrect Token";
    }
    VerificationToken registeredToken = registeredTokenOpt.get();

    if (registeredToken.getExpiryAt().before(new Date())) {
      _userRepository.delete(registeredToken.getUser());
      _verificationTokenRepository.delete(registeredToken);
      return "Verification token expired, please re-register yourself";
    }

    User user = registeredToken.getUser();
    user.setEnabled(true);
    _verificationTokenRepository.delete(registeredToken);
    _userRepository.save(user);

    return "Verification Successful, Please login yourself into the system";
  }


  // Fetch the user and see if the user exists
  // if the user does not exists you can ask them register
  // If the user exists
  // If the user is not enabled, you can ask them to verify their email address
  // if the user is enabled, we can check for the correctness of the password
  // Generate JWT token and return the response
  public String signinUser(String username, String password) {
    Optional<User> userOptional = _userRepository.findByUsername(username);
    if (!userOptional.isPresent()) {
      return "User not found with username: " + username + " Please register yourself";
    }

    User user = userOptional.get();
    if (!user.isEnabled()) {
      return "User is not enabled, please verify your email address before signing in.";
    }

    boolean isPasswordMatching = _passwordEncoder.matches(password, user.getPassword());
    if (!isPasswordMatching) {
      return "Invalid password, please provide correct credentials.";
    }

    // Passwords are matching and we generate the JWT token
    return JwtUtil.generateJWTtoken(user);

  }
}
