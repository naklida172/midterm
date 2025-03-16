package kg.alatoo.midterm.services.implementation;

import kg.alatoo.midterm.dtos.OrderDTO;
import kg.alatoo.midterm.entities.Order;
import kg.alatoo.midterm.exceptions.ResourceNotFoundException;
import kg.alatoo.midterm.mappers.OrderMapper;
import kg.alatoo.midterm.repositories.OrderRepository;
import kg.alatoo.midterm.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order createOrder(OrderDTO orderDTO) {
        if (orderDTO == null) {
            throw new IllegalArgumentException("OrderDTO cannot be null.");
        }

        Order order = OrderMapper.toEntity(orderDTO);
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Order ID cannot be null.");
        }

        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            throw new ResourceNotFoundException("No orders found.");
        }
        return orders;
    }

    @Override
    public Order updateOrder(Long id, OrderDTO orderDTO) {
        if (id == null || orderDTO == null) {
            throw new IllegalArgumentException("Order ID and OrderDTO cannot be null.");
        }

        Order existingOrder = getOrderById(id);

        existingOrder.setOrderDate(orderDTO.getOrderDate());
        existingOrder.setStatus(orderDTO.getStatus());
        existingOrder.setQuantity(orderDTO.getQuantity());

        return orderRepository.save(existingOrder);
    }

    @Override
    public void deleteOrder(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Order ID cannot be null.");
        }

        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Order not found with id: " + id);
        }

        orderRepository.deleteById(id);
    }
}
