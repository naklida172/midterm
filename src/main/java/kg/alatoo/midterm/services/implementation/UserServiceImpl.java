package kg.alatoo.midterm.services.implementation;

import kg.alatoo.midterm.dtos.UserDTO;
import kg.alatoo.midterm.entities.User;
import kg.alatoo.midterm.exceptions.ResourceNotFoundException;
import kg.alatoo.midterm.mappers.UserMapper;
import kg.alatoo.midterm.repositories.UserRepository;
import kg.alatoo.midterm.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(UserDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException("UserDTO cannot be null.");
        }

        User user = UserMapper.toEntity(userDTO);
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null.");
        }

        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new ResourceNotFoundException("No users found.");
        }
        return users;
    }

    @Override
    public User updateUser(Long id, UserDTO userDTO) {
        if (id == null || userDTO == null) {
            throw new IllegalArgumentException("ID and UserDTO cannot be null.");
        }

        User existingUser = getUserById(id);
        existingUser.setName(userDTO.getName());
        existingUser.setUsername(userDTO.getUsername());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setPhone(userDTO.getPhone());
        existingUser.setRole(userDTO.getRole());

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null.");
        }

        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }

        userRepository.deleteById(id);
    }
}
