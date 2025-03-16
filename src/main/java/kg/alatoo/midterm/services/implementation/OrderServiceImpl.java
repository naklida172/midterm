package kg.alatoo.midterm.services.implementation;

import kg.alatoo.midterm.dtos.OrderDTO;
import kg.alatoo.midterm.entities.Order;
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
        Order order = OrderMapper.toEntity(orderDTO);
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order updateOrder(Long id, OrderDTO orderDTO) {
        Order existingOrder = getOrderById(id);
        existingOrder.setOrderDate(orderDTO.getOrderDate());
        existingOrder.setStatus(orderDTO.getStatus());
        existingOrder.setQuantity(orderDTO.getQuantity());
        return orderRepository.save(existingOrder);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
