package br.com.place.estudoSpringSecurity.config;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration //diz ao spring que essa classe é uma fábica de beans, spring vai buscar métodos com @Bean
@EnableWebSecurity //"Desative as configurações padrão de segurança (aquela senha aleatória que gera no console) porque eu vou ditar as regras a partir de agora nesta classe".
public class SecurityConfig {

    @Bean
    //O securityFilterChain é o jeito que o spring vai proteger o app
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
    {
        /*@Bean: O Spring vai executar esse método securityFilterChain uma única vez no início da aplicação.
         O objeto que esse método retornar será guardado no contexto do Spring.
         SecurityFilterChain: É o coração do Spring Security. Ele é, literalmente,
         uma "corrente de filtros" pela qual toda requisição HTTP (como um GET /produtos ou POST /login)
         deve passar antes de chegar no seu @RestController.
         HttpSecurity http: Você não instanciou esse objeto http, certo?
         O Spring o injeta ali para você automaticamente (Injeção de Dependência no parâmetro).
         Ele serve como uma "massa de modelar" para você definir quem pode acessar o quê.*/

        return http
                //A API não usará cookies tradicionais
                .csrf(AbstractHttpConfigurer::disable) //"Spring, pegue o configurador padrão de CSRF (que eu apelidei de csrf antes da setinha ->) e execute o método .disable() nele."
                //Ativa e configura o Cross-Origin Resources Sharing
                .cors(cors -> cors.configure(http))
                //"Eu sei que o HTTP é stateless, mas eu quero que você, Spring Security,
                // também aja de forma totalmente stateless.
                // Desative o seu gerenciador de sessões interno,
                // não crie Cookies, não guarde nada na memória do servidor.
                // Eu mesmo vou cuidar da autenticação de cada requisição manualmente usando tokens".
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .anyRequest().authenticated())
                .build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception
    {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
