package io.github.guilherme_s_barros.auth_spring_api.controllers;

import io.github.guilherme_s_barros.auth_spring_api.domain.user.User;
import io.github.guilherme_s_barros.auth_spring_api.dto.LoginRequestDTO;
import io.github.guilherme_s_barros.auth_spring_api.dto.RegisterRequestDTO;
import io.github.guilherme_s_barros.auth_spring_api.dto.ResponseDTO;
import io.github.guilherme_s_barros.auth_spring_api.infra.security.TokenService;
import io.github.guilherme_s_barros.auth_spring_api.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
  private final UsersRepository usersRepository;
  private final PasswordEncoder passwordEncoder;
  private final TokenService tokenService;

  @PostMapping("/login")
  public ResponseEntity<ResponseDTO> login(
    @RequestBody LoginRequestDTO body
  ) {
    var user = usersRepository
      .findByEmail(body.email())
      .orElseThrow(() -> new RuntimeException("User not found."));

    if (!passwordEncoder.matches(body.password(), user.getPassword())) {
      return ResponseEntity.badRequest().build();
    }

    var token = tokenService.generateToken(user);

    return ResponseEntity.ok().body(new ResponseDTO(user.getName(), token));
  }

  @PostMapping("/register")
  public ResponseEntity<ResponseDTO> register(
    @RequestBody RegisterRequestDTO body
  ) {
    var userWithSameEmail = usersRepository.findByEmail(body.email());

    if (userWithSameEmail.isPresent()) {
      return ResponseEntity.badRequest().build();
    }

    var user = new User();

    user.setEmail(body.email());
    user.setName(body.name());
    user.setPassword(passwordEncoder.encode(body.password()));

    usersRepository.save(user);
    var token = tokenService.generateToken(user);

    return ResponseEntity.ok().body(new ResponseDTO(user.getName(), token));
  }
}
