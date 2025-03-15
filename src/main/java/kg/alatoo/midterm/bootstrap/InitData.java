package kg.alatoo.midterm.bootstrap;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import kg.alatoo.midterm.entities.*;
import kg.alatoo.midterm.repositories.OrderRepository;
import kg.alatoo.midterm.repositories.PointRepository;
import kg.alatoo.midterm.repositories.ProductRepository;
import kg.alatoo.midterm.repositories.SellerRepository;
import kg.alatoo.midterm.repositories.TagRepository;
import kg.alatoo.midterm.repositories.UserRepository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    private Map<Long, Seller> sellers = new HashMap<>();
    private Map<Long, User> users = new HashMap<>();
    private Map<Long, Product> products = new HashMap<>();
    private Map<Long, Point> points = new HashMap<>();
    private Map<Long, Tag> tags = new HashMap<>();

    private long currentId = 1;

    @PostConstruct
    public void init() {
        System.out.println("Creating values from src\\main\\java\\kg\\alatoo\\midterm\\bootstrap\\InitData.csv");
        

    }

    @PreDestroy
    public void destroy() {
        System.out.println("Cleaning up resources");
    }
}
