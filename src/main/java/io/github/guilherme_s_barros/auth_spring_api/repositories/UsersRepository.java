package io.github.guilherme_s_barros.auth_spring_api.repositories;

import io.github.guilherme_s_barros.auth_spring_api.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsersRepository extends JpaRepository<User, UUID> {
  Optional<User> findByEmail(String email);
}
