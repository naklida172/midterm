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
public class TagDTO {
    private Long id;
    private String name;
    private String description;
    private List<Long> productIds; // List of FK to Products
}
