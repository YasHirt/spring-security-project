package br.com.place.estudoSpringSecurity.config;

import br.com.place.estudoSpringSecurity.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;


@Component //service
public class TokenConfig {
    private String secret = "secret";

    public String generateToken(User user)
    {
        /*Aqui definimos o algoritmo de criptografia e passmosa a palavra secreta ("secret").
         Essa chave é o que garante que apenas a nossa API consiga gerar e validar esses tokens.
         Se um hacker tentar alterar o token para dizer que ele é o administrador,
         a assinatura digital vai quebrar porque ele não tem a palavra secreta.*/
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withClaim("UserId", user.getId())
                .withSubject(user.getEmail())
                .withExpiresAt(Instant.now().plusSeconds(867000))
                .withIssuedAt(Instant.now())
                .sign(algorithm);



    }

    public Optional<JWTUserData> validateToken(String token) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            DecodedJWT decode = JWT.require(algorithm).build().verify(token);
            return Optional.of(JWTUserData.builder()
                    .userId(decode.getClaim("userId").asLong())
                    .email(decode.getSubject())
                    .build());
        }catch (JWTVerificationException ex)
        {
            return Optional.empty();
        }


    }
}
