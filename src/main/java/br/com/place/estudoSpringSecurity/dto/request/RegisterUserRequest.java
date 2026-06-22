package br.com.place.estudoSpringSecurity.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record RegisterUserRequest(
        @NotEmpty(message = "Nome é obrigatório")
        String name,
        @NotEmpty(message = "Email é obrigatório")
        String email,
        @NotEmpty(message = "Senha é onrigatória")
        String password) {
}
