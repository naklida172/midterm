package kg.alatoo.midterm.dtos;

import java.util.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AuthTokenDTO {
    private long id;
    private UUID token;
    private Date createdAt;
    private Date expiresAt;
    private Long userId; // FK to User
}

