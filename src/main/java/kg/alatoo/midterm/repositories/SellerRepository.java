package kg.alatoo.midterm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kg.alatoo.midterm.entities.Seller;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {

}
