package kg.alatoo.midterm.repositories;

import kg.alatoo.midterm.entities.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class OrderRepositoryTest {
    //Due to all repositories having the same structure a test for a singular one is enough
    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void testSaveOrder() {
        Order order = Order.builder()
                .orderDate(new Date())
                .quantity((short) 3)
                .status("Pending")
                .build();

        Order savedOrder = orderRepository.save(order);

        assertNotNull(savedOrder.getId());
        assertEquals("Pending", savedOrder.getStatus());
        assertEquals(3, savedOrder.getQuantity());
    }

    @Test
    public void testFindOrderById() {
        Order order = Order.builder()
                .orderDate(new Date())
                .quantity((short) 5)
                .status("Shipped")
                .build();
        Order savedOrder = orderRepository.save(order);

        Optional<Order> retrievedOrder = orderRepository.findById(savedOrder.getId());

        assertTrue(retrievedOrder.isPresent());
        assertEquals("Shipped", retrievedOrder.get().getStatus());
        assertEquals(5, retrievedOrder.get().getQuantity());
    }

    @Test
    public void testUpdateOrder() {
        Order order = Order.builder()
                .orderDate(new Date())
                .quantity((short) 2)
                .status("Pending")
                .build();
        Order savedOrder = orderRepository.save(order);

        savedOrder.setStatus("Completed");
        Order updatedOrder = orderRepository.save(savedOrder);

        assertEquals("Completed", updatedOrder.getStatus());
    }

    @Test
    public void testDeleteOrder() {
        Order order = Order.builder()
                .orderDate(new Date())
                .quantity((short) 4)
                .status("Cancelled")
                .build();
        Order savedOrder = orderRepository.save(order);

        orderRepository.delete(savedOrder);

        Optional<Order> deletedOrder = orderRepository.findById(savedOrder.getId());
        assertFalse(deletedOrder.isPresent());
    }
}
