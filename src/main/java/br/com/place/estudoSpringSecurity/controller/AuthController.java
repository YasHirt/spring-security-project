package br.com.place.estudoSpringSecurity.controller;

import br.com.place.estudoSpringSecurity.dto.request.LoginRequest;
import br.com.place.estudoSpringSecurity.dto.request.RegisterUserRequest;
import br.com.place.estudoSpringSecurity.dto.response.LoginResponse;
import br.com.place.estudoSpringSecurity.dto.response.RegisterUserResponse;
import br.com.place.estudoSpringSecurity.entity.User;
import br.com.place.estudoSpringSecurity.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//Stereotype Component Scanning
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login") //essa dto retorna um token
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest)
    {
        UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password());
        Authentication authentication = authenticationManager.authenticate(userAndPass);
        return null;
    }
    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> register(@Valid @RequestBody RegisterUserRequest registerUserRequest)
    {
        //TODO: criar o services de Auth
        User newUser = new User();
        newUser.setEmail(registerUserRequest.email());
        newUser.setName(registerUserRequest.name());
        newUser.setPassword(passwordEncoder.encode(registerUserRequest.password()));
        userRepository.save(newUser);
        return ResponseEntity.ok().body(new RegisterUserResponse(newUser.getName(), newUser.getEmail()));
    }

}
