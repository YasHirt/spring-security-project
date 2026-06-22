package br.com.place.estudoSpringSecurity.repository;

import br.com.place.estudoSpringSecurity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
     Optional<UserDetails> findUserByEmail(String username);
}
