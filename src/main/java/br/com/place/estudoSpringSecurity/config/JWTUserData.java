package br.com.place.estudoSpringSecurity.config;

import lombok.Builder;

@Builder
public record JWTUserData(Long userId, String email) {

}
