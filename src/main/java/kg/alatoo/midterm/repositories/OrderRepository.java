package kg.alatoo.midterm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import kg.alatoo.midterm.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
    
}
