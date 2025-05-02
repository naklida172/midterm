package kg.alatoo.midterm.services;

import kg.alatoo.midterm.entities.AuthToken;

public interface AuthService {
    AuthToken createAuthToken(String username, String password);
}
