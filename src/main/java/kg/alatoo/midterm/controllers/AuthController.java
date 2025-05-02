package kg.alatoo.midterm.controllers;

import kg.alatoo.midterm.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService AuthService;

    // @PostMapping("/login")
    // public Auth createAuth(@RequestBody AuthDTO AuthDTO) {
    //     return AuthService.createAuthToken(AuthDTO);
    // }
}
