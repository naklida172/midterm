package kg.alatoo.midterm.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import kg.alatoo.midterm.dtos.AuthTokenDTO;
import kg.alatoo.midterm.dtos.LoginRequest;
import kg.alatoo.midterm.entities.AuthToken;
import kg.alatoo.midterm.exceptions.InvalidCredentialsException;
import kg.alatoo.midterm.mappers.AuthTokenMapper;
import kg.alatoo.midterm.repositories.AuthTokenRepository;
import kg.alatoo.midterm.services.AuthService;

@EnableWebMvc
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthTokenDTO> createAuth(@RequestBody LoginRequest loginRequest) {
        AuthToken authToken = authService.createAuthToken(loginRequest.getUsername(), loginRequest.getPassword());
        if (authToken != null) {
            return ResponseEntity.ok(AuthTokenMapper.toDTO(authToken)); // Returns 200 OK
        }
        throw new InvalidCredentialsException();
    }

    // New endpoint to retrieve all stored authentication tokens
    @Autowired
    private AuthTokenRepository authTokenRepository;

    @GetMapping("/tokens")
    public ResponseEntity<List<AuthTokenDTO>> getAllAuthTokens() {
        List<AuthTokenDTO> tokenDTOs = authTokenRepository.findAll().stream()
                .map(AuthTokenMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(tokenDTOs);
    }
}
