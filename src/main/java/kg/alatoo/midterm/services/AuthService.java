package kg.alatoo.midterm.services;

import kg.alatoo.midterm.entities.AuthToken;
import kg.alatoo.midterm.entities.User;

public interface AuthService {
    AuthToken createAuthToken(String username, String password);
    User registerUser(String username, String email, String password);
}
