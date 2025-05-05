package kg.alatoo.midterm.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistrationResponseDTO {
    private UserDTO user;
    private AuthTokenDTO authToken;
}
