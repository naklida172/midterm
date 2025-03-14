package kg.alatoo.midterm.entities;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue
    private long id;
    private Date orderDate;
    private short quantity;
    private String status;
    @ManyToOne
    private Product product;
    @ManyToOne
    private Point point;
    @ManyToOne
    private User user;
}
