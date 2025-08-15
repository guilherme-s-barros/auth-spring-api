package io.github.guilherme_s_barros.auth_spring_api.dto;

public record LoginRequestDTO(
  String email,
  String password
) { }
