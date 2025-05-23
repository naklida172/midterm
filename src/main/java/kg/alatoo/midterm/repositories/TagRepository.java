package kg.alatoo.midterm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kg.alatoo.midterm.entities.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

}
