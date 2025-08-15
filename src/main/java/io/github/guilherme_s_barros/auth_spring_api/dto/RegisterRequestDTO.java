package io.github.guilherme_s_barros.auth_spring_api.dto;

public record RegisterRequestDTO(
  String name,
  String email,
  String password
) { }
