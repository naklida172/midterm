package kg.alatoo.midterm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kg.alatoo.midterm.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
