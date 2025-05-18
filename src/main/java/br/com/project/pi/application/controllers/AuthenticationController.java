package br.com.project.pi.application.controllers;

import br.com.project.pi.application.dto.AuthenticationDTO;
import br.com.project.pi.application.dto.EmailResponseDTO;
import br.com.project.pi.application.dto.RegisterDTO;
import br.com.project.pi.application.exception.EmailAlreadyExistsException;
import br.com.project.pi.application.exception.PasswordIncorrectException;
import br.com.project.pi.application.exception.UserNotFoundException;
import br.com.project.pi.application.infra.segurity.TokenService;
import br.com.project.pi.application.model.User;
import br.com.project.pi.application.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")

public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO data) {

        var userOptional = repository.findByEmail(data.email());

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException();
        }
            var authenticationToken = new UsernamePasswordAuthenticationToken(
                    data.email(),
                    data.password()
            );
        try {
            var authentication = authenticationManager.authenticate(authenticationToken);

            var user = (User) authentication.getPrincipal();
            var token = tokenService.generateToken(user);

            return ResponseEntity.ok(new EmailResponseDTO(token));

        }
        catch (BadCredentialsException e) {
            throw new PasswordIncorrectException();
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {

        if (repository.findByEmail(data.email()).isPresent()) {
            throw new EmailAlreadyExistsException();
        }

        String encryptedPassord = new BCryptPasswordEncoder().encode(data.password()); /// GUARDA UMA SENHA CRIPTOGRAFADA

        User newUser = new User(data.name(), data.email(), encryptedPassord);
        this.repository.save(newUser); /// CRIA E SALVA UM USUARIO NO BANCO DE DADOS

        var userNamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(userNamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new EmailResponseDTO(token));
    }
}
