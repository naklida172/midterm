package kg.alatoo.midterm.bootstrap;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import kg.alatoo.midterm.entities.*;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitData {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PointRepository pointRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private TagRepository tagRepository;

    @PostConstruct
    public void init() {

        System.out.println("Initializing data");

        Seller seller = new Seller(0, "SellerName", null);
        sellerRepository.save(seller);

        Product product = new Product(0, "ProductName", seller, "Description", (short) 5, null);
        productRepository.save(product);

        User user = new User(0, "UserName", "Username", "Role", "Email", "Phone", null);
        userRepository.save(user);

        Point point = new Point(0, "Address", "Status", "WorkTime", null);
        pointRepository.save(point);

        Order order = new Order(0, new Date(), (short) 10, "Status", product, point, user);
        orderRepository.save(order);

        Tag tag = new Tag(0, "TagName", "Description", null);
        tagRepository.save(tag);

        System.out.println("Data initialization completed.");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Cleaning up resources");
    }
}
