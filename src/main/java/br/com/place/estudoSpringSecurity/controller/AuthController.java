package br.com.place.estudoSpringSecurity.controller;

import br.com.place.estudoSpringSecurity.dto.request.LoginRequest;
import br.com.place.estudoSpringSecurity.dto.request.RegisterUserRequest;
import br.com.place.estudoSpringSecurity.dto.response.LoginResponse;
import br.com.place.estudoSpringSecurity.dto.response.RegisterUserResponse;
import br.com.place.estudoSpringSecurity.entity.User;
import br.com.place.estudoSpringSecurity.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//Stereotype Component Scanning
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login") //essa dto retorna um token
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest)
    {
        return null;
    }
    @PostMapping
    public ResponseEntity<RegisterUserResponse> register(@Valid @RequestBody RegisterUserRequest registerUserRequest)
    {
        //TODO: criar o services de Auth
        User newUser = new User();
        newUser.setEmail(registerUserRequest.email());
        newUser.setName(registerUserRequest.name());
        newUser.setPassword(registerUserRequest.password());
        userRepository.save(newUser);
        return ResponseEntity.ok().body(new RegisterUserResponse(newUser.getName(), newUser.getEmail()));
    }

}
