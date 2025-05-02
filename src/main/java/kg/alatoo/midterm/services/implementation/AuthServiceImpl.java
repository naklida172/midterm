package kg.alatoo.midterm.services.implementation;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kg.alatoo.midterm.entities.AuthToken;
import kg.alatoo.midterm.entities.User;
import kg.alatoo.midterm.repositories.AuthTokenRepository;
import kg.alatoo.midterm.repositories.UserRepository;
import kg.alatoo.midterm.services.AuthService;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private AuthTokenRepository authTokenRepository;

    @Autowired
    private UserRepository userRepository; //For password check

    @Override
    public AuthToken createAuthToken(String username, String password) {
        // Optional<User> userOptional = userRepository.findByUsername(username);

        // if (userOptional.isPresent() && User.matches(password, userOptional.get().getPassword())) { //for now there is no password
        //     UUID token = UUID.randomUUID();

        //     AuthToken authToken = AuthToken.builder()
        //         .user(userOptional.get())
        //         .token(token)
        //         .createdAt(new java.util.Date())
        //         .build();

        //     return authTokenRepository.save(authToken);
        // }

        return null; // Handle failure scenario appropriately
    }
}
