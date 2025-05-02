package kg.alatoo.midterm.controllers;

import kg.alatoo.midterm.dtos.AuthTokenDTO;
import kg.alatoo.midterm.dtos.LoginRequest;
import kg.alatoo.midterm.entities.AuthToken;
import kg.alatoo.midterm.exceptions.InvalidCredentialsException;
import kg.alatoo.midterm.mappers.AuthTokenMapper;
import kg.alatoo.midterm.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

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
}
