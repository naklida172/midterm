package kg.alatoo.midterm.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PointDTO {
    private Long id;
    private String address;
    private String status;
    private String workTime;
    private List<Long> orderIds; // List of FK to Orders
}
