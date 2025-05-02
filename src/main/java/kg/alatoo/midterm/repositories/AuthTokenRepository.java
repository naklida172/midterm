package kg.alatoo.midterm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kg.alatoo.midterm.entities.AuthToken;

@Repository
public interface AuthTokenRepository extends JpaRepository<AuthToken, Long>{
    
}
