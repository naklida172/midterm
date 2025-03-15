package kg.alatoo.midterm.mappers;

import kg.alatoo.midterm.dtos.OrderDTO;
import kg.alatoo.midterm.entities.Order;
import kg.alatoo.midterm.entities.Point;
import kg.alatoo.midterm.entities.Product;
import kg.alatoo.midterm.entities.User;

public class OrderMapper {
    public static OrderDTO toDTO(Order order) {
        if (order == null) return null;
        return OrderDTO.builder()
                .id(order.getId())
                .orderDate(order.getOrderDate())
                .quantity(order.getQuantity())
                .status(order.getStatus())
                .productId(order.getProduct() != null ? order.getProduct().getId() : null)
                .pointId(order.getPoint() != null ? order.getPoint().getId() : null)
                .userId(order.getUser() != null ? order.getUser().getId() : null)
                .build();
    }

    public static Order toEntity(OrderDTO orderDTO, Product product, Point point, User user) {
        if (orderDTO == null) return null;
        return Order.builder()
                .id(orderDTO.getId())
                .orderDate(orderDTO.getOrderDate())
                .quantity(orderDTO.getQuantity())
                .status(orderDTO.getStatus())
                .product(product)
                .point(point)
                .user(user)
                .build();
    }
}
