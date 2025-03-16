package kg.alatoo.midterm.services;

import kg.alatoo.midterm.dtos.OrderDTO;
import kg.alatoo.midterm.entities.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(OrderDTO orderDTO);
    Order getOrderById(Long id);
    List<Order> getAllOrders();
    Order updateOrder(Long id, OrderDTO orderDTO);
    void deleteOrder(Long id);
}
