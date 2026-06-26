package br.com.place.estudoSpringSecurity.config;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Optional;

//Recupera o token do cabeçalho Authorization: Bearer <token>.
//Usa o seu TokenService para validar o token e extrair o username.
//Busca o usuário no banco de dados (via UserRepository ou UserDetailsService).
//Cria um objeto UsernamePasswordAuthenticationToken e o coloca dentro do SecurityContextHolder.
// Isso avisa o Spring: "Esse cara tá autenticado, pode deixar passar".
//Chama filterChain.doFilter(request, response) para seguir viagem.
@Component
public class SecurityFilter extends OncePerRequestFilter {
    private final TokenConfig tokenConfig;

    public SecurityFilter(TokenConfig tokenConfig) {
        this.tokenConfig = tokenConfig; //O token config gera o token
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
                String authorizedHeader = request.getHeader("Authorization");
                if (Strings.isNotEmpty(authorizedHeader) && authorizedHeader.startsWith("Bearer"))
                {
                    String token = authorizedHeader.substring("Bearer ".length());
                    Optional<JWTUserData> optUser = tokenConfig.validateToken(token);
                    if (optUser.isPresent())
                    {
                        JWTUserData userData = optUser.get();
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userData.userId(), null);
                        SecurityContextHolder.getContext().setAuthentication(authentication);

                    }
                    filterChain.doFilter(request, response);

                }
                else {
                    filterChain.doFilter(request, response);
                }

    }
}
