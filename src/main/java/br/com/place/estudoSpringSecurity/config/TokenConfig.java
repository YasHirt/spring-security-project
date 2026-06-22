package br.com.place.estudoSpringSecurity.config;

import br.com.place.estudoSpringSecurity.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

import java.time.Instant;


@Component
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
}
