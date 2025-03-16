package kg.alatoo.midterm.integration;

import kg.alatoo.midterm.entities.User;
import kg.alatoo.midterm.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class UserIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void interactionLayersSimplifyFlowsValidation() {
        User user = new User();
        user.setName("John Doe");
        user.setUsername("johndoe");
        user.setEmail("johndoe@example.com");
        user.setRole("Customer");
        user.setPhone("+123456789");
        user = userRepository.save(user);

        User retrievedUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        assertThat(retrievedUser).isNotNull();
        assertThat(retrievedUser.getId()).isEqualTo(user.getId());
        assertThat(retrievedUser.getName()).isEqualTo("John Doe");
        assertThat(retrievedUser.getUsername()).isEqualTo("johndoe");
        assertThat(retrievedUser.getEmail()).isEqualTo("johndoe@example.com");
        assertThat(retrievedUser.getRole()).isEqualTo("Customer");
        assertThat(retrievedUser.getPhone()).isEqualTo("+123456789");
    }
}
