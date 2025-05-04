package kg.alatoo.midterm.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kg.alatoo.midterm.entities.AuthToken;

@Repository
public interface AuthTokenRepository extends JpaRepository<AuthToken, Long>{
    Optional<AuthToken> findByToken(UUID token);
}
