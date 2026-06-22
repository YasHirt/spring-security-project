package br.com.place.estudoSpringSecurity.config;

import br.com.place.estudoSpringSecurity.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
//O Spring Security tem uma interface nativa chamada UserDetailsService.
// Ela só serve para uma coisa: dizer ao framework como buscar um usuário no seu banco de dados.
@Service
public class AuthConfig implements UserDetailsService {

    private final UserRepository userRepository;

    public AuthConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException(username)
        );
    }
}
