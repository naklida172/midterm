package kg.alatoo.midterm.mappers;

import kg.alatoo.midterm.entities.Order;
import kg.alatoo.midterm.dtos.OrderDTO;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class OrderMapperTest {
    //Due to all mappers having the same structure a test for a singular one is enough
    @Test
    public void testToEntity() {
        OrderDTO dto = OrderDTO.builder()
                .id(1L)
                .orderDate(new Date())
                .status("Pending")
                .quantity((short) 3)
                .build();

        Order entity = OrderMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.getOrderDate(), entity.getOrderDate());
        assertEquals(dto.getStatus(), entity.getStatus());
        assertEquals(dto.getQuantity(), entity.getQuantity());
    }

    @Test
    public void testToDTO() {
        Order entity = Order.builder()
                .id(2L)
                .orderDate(new Date())
                .status("Completed")
                .quantity((short) 5)
                .build();
        OrderDTO dto = OrderMapper.toDTO(entity);


        assertNotNull(dto);
        assertEquals(entity.getOrderDate(), dto.getOrderDate());
        assertEquals(entity.getStatus(), dto.getStatus());
        assertEquals(entity.getQuantity(), dto.getQuantity());
    }
}
