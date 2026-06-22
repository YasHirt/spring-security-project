package br.com.place.estudoSpringSecurity.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(
        @NotEmpty(message = "Email não pode estar vazio")
        String email,
        @NotEmpty(message = "Senha não pode estar vazia")
        String password) {
}
