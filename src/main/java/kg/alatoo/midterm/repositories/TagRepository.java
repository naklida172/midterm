package kg.alatoo.midterm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



import javax.swing.text.html.HTML.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

}
