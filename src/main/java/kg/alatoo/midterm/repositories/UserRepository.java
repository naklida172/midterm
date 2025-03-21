package kg.alatoo.midterm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kg.alatoo.midterm.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
