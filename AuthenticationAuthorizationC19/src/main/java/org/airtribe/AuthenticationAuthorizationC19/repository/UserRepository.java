package org.airtribe.AuthenticationAuthorizationC19.repository;

import java.util.Optional;
import org.airtribe.AuthenticationAuthorizationC19.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  public Optional<User> findByEmail(String email);

  public Optional<User> findByUsername(String username);
}
// Brute force attack


