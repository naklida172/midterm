package kg.alatoo.midterm.services;

import java.util.List;

import kg.alatoo.midterm.dtos.UserDTO;
import kg.alatoo.midterm.entities.User;

public interface UserService {
    User createUser(UserDTO userDTO);
    User getUserById(Long id);
    List<User> getAllUsers();
    User updateUser(Long id, UserDTO userDTO);
    void deleteUser(Long id);
    List<UserDTO> getAllUsersDTO();
    UserDTO getUserByIdDTO(Long id);
    UserDTO updateUserDTO(Long id, UserDTO userDTO);
}

