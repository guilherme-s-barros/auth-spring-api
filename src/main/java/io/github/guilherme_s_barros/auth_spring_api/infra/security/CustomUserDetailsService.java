package io.github.guilherme_s_barros.auth_spring_api.infra.security;

import io.github.guilherme_s_barros.auth_spring_api.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class CustomUserDetailsService implements UserDetailsService {
  @Autowired private UsersRepository usersRepository;

  @Override
  public UserDetails loadUserByUsername(String username)
  throws UsernameNotFoundException {
    var user = usersRepository
      .findByEmail(username)
      .orElseThrow(() -> new UsernameNotFoundException("User not found."));

    return new User(
      user.getEmail(),
      user.getPassword(),
      Collections.emptyList()
    );
  }
}
