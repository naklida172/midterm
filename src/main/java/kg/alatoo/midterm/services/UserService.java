package kg.alatoo.midterm.services;

import kg.alatoo.midterm.dtos.UserDTO;
import kg.alatoo.midterm.entities.User;

import java.util.List;

public interface UserService {
    User createUser(UserDTO userDTO);
    User getUserById(Long id);
    List<User> getAllUsers();
    User updateUser(Long id, UserDTO userDTO);
    void deleteUser(Long id);
}

