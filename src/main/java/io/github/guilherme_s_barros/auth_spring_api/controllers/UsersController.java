package io.github.guilherme_s_barros.auth_spring_api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {
  @GetMapping()
  public ResponseEntity<String> example() {
    return ResponseEntity.ok("Sucesso!");
  }
}
