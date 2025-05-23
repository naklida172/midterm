package kg.alatoo.midterm.mappers;

import java.util.ArrayList;
import java.util.List;

import kg.alatoo.midterm.dtos.PointDTO;
import kg.alatoo.midterm.entities.Order;
import kg.alatoo.midterm.entities.Point;

public class PointMapper {
    public static PointDTO toDTO(Point point) {
        if (point == null) return null;
        return PointDTO.builder()
                .id(point.getId())
                .address(point.getAddress())
                .status(point.getStatus())
                .workTime(point.getWorkTime())
                .orderIds(point.getOrders() != null
                        ? point.getOrders().stream().map(Order::getId).toList()
                        : null)
                .build();
    }

    public static Point toEntity(PointDTO pointDTO) {
        if (pointDTO == null) return null;
    
        List<Order> orders = new ArrayList<>();
        if (pointDTO.getOrderIds() != null) {
            for (Long orderIter : pointDTO.getOrderIds()) {
                orders.add(Order.builder().id(orderIter).build());
            }
        }
    
        return Point.builder()
                .address(pointDTO.getAddress())
                .status(pointDTO.getStatus())
                .workTime(pointDTO.getWorkTime())
                .orders(orders)
                .build();
    }
    
}
