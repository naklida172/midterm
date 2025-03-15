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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
    private Map<Long, Order> orders = new HashMap<>();

    private long currentId = 1;

    @PostConstruct
    public void init() {
        System.out.println("Creating values from src\\main\\java\\kg\\alatoo\\midterm\\bootstrap\\InitData.csv");
        try (BufferedReader csValues = new BufferedReader(new FileReader("init-data.csv"))) {
            String line;
            while ((line = csValues.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length < 3)
                    continue;
                String entity = values[0];
                String type = values[1];
                String value = values[2];
                processLine(entity, type, value );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Data creation from csv file is complete");
    }

    private void processLine(String entity, String type, String value) {
        switch (entity) {
            case "Seller":
                if (type.equals("name")) {
                    Seller seller = Seller.builder()
                            .id(currentId++)           
                            .name(value)               
                            .user(new ArrayList<>())   
                            .build();
                    sellers.put(seller.getId(), seller);
                    sellerRepository.save(seller);
                } else if (type.equals("userRelation") && value != null && !value.trim().isEmpty()) {
                    Seller seller = sellers.get(currentId - 1);
                    if (seller != null) {
                        String[] userIds = value.split(";");
                        for (String userIdStr : userIds) {
                            try {
                                Long userId = Long.parseLong(userIdStr.trim());
                                User user = users.get(userId);
                                if (user != null) {
                                    seller.getUser().add(user);
                                    user.getSellers().add(seller);
                                    userRepository.save(user);
                                }
                            } catch (NumberFormatException e) {
                                System.err.println("Invalid user id format: " + userIdStr);
                            }
                        }
                        sellerRepository.save(seller);
                    }
                }
                break;
            case "User":
                if (type.equals("name")) {
                    // For Users, the name is in the value field.
                    User user = User.builder()
                            .id(currentId++)
                            .name(value)
                            .username(null)
                            .role(null)
                            .email(null)
                            .phone(null)
                            .sellers(new ArrayList<>())
                            .build();
                    users.put(user.getId(), user);
                    userRepository.save(user);
                } else if (type.equals("username")) {
                    User user = users.get(currentId - 1);
                    user.setUsername(value);
                    userRepository.save(user);
                } else if (type.equals("role")) {
                    User user = users.get(currentId - 1);
                    user.setRole(value);
                    userRepository.save(user);
                } else if (type.equals("email")) {
                    User user = users.get(currentId - 1);
                    user.setEmail(value);
                    userRepository.save(user);
                } else if (type.equals("phone")) {
                    User user = users.get(currentId - 1);
                    user.setPhone(value);
                    userRepository.save(user);
                }
                break;

            case "Product":
                if (type.equals("name")) {
                    Seller productSeller = sellers.get(Long.parseLong(value));
                    Product product = Product.builder()
                            .id(currentId++)
                            .name("Unnamed Product") 
                            .description(null)
                            .rating((short) 0)
                            .seller(productSeller)
                            .tags(new ArrayList<>())
                            .build();
                    products.put(product.getId(), product);
                    productRepository.save(product);
                } else if (type.equals("description")) {
                    Product product = products.get(currentId - 1);
                    product.setDescription(value);
                    productRepository.save(product);
                } else if (type.equals("rating")) {
                    Product product = products.get(currentId - 1);
                    product.setRating(Short.parseShort(value));
                    productRepository.save(product);
                } else if (type.equals("tagRelation") && value != null && !value.trim().isEmpty()) {
                    Product product = products.get(currentId - 1);
                    if (product != null) {
                        String[] tagIds = value.split(";");
                        for (String tagIdStr : tagIds) {
                            try {
                                Long tagId = Long.parseLong(tagIdStr.trim());
                                Tag tag = tags.get(tagId);
                                if (tag != null) {
                                    product.getTags().add(tag);
                                    tag.getProducts().add(product);
                                    tagRepository.save(tag);
                                }
                            } catch (NumberFormatException e) {
                                System.err.println("Invalid tag id format: " + tagIdStr);
                            }
                        }
                        productRepository.save(product);
                    }
                }
                break;
            case "Tag":
                if (type.equals("name")) {
                    Tag tag = Tag.builder()
                            .id(currentId++)
                            .name(value)
                            .description(null)
                            .products(new ArrayList<>())
                            .build();
                    tags.put(tag.getId(), tag);
                    tagRepository.save(tag);
                } else if (type.equals("description")) {
                    Tag tag = tags.get(currentId - 1);
                    tag.setDescription(value);
                    tagRepository.save(tag);
                }
                break;
            case "Point":
                if (type.equals("address")) {
                    Point point = Point.builder()
                            .id(currentId++)
                            .address(value)
                            .status(null)
                            .workTime(null)
                            .orders(new ArrayList<>())
                            .build();
                    points.put(point.getId(), point);
                    pointRepository.save(point);
                } else if (type.equals("status")) {
                    Point point = points.get(currentId - 1);
                    point.setStatus(value);
                    pointRepository.save(point);
                } else if (type.equals("workTime")) {
                    Point point = points.get(currentId - 1);
                    point.setWorkTime(value);
                    pointRepository.save(point);
                }
                break;
            case "Order":
                if (type.equals("orderDate")) {
                    Order order = Order.builder()
                            .id(currentId++)
                            .orderDate(new Date())
                            .quantity((short) 0)
                            .status("")
                            .build();
                    orders.put(order.getId(), order);
                    orderRepository.save(order);
                } else if (type.equals("orderProduct") && value != null && !value.trim().isEmpty()) {
                    Order order = orders.get(currentId - 1);
                    if (order != null) {
                        Product orderProduct = products.get(Long.parseLong(value));
                        order.setProduct(orderProduct);
                        orderRepository.save(order);
                    }
                } else if (type.equals("orderPoint") && value != null && !value.trim().isEmpty()) {
                    Order order = orders.get(currentId - 1);
                    if (order != null) {
                        Point orderPoint = points.get(Long.parseLong(value));
                        order.setPoint(orderPoint);
                        orderRepository.save(order);
                    }
                } else if (type.equals("orderUser") && value != null && !value.trim().isEmpty()) {
                    Order order = orders.get(currentId - 1);
                    if (order != null) {
                        User orderUser = users.get(Long.parseLong(value));
                        order.setUser(orderUser);
                        orderRepository.save(order);
                    }
                } else if (type.equals("quantity")) {
                    Order order = orders.get(currentId - 1);
                    if (order != null) {
                        order.setQuantity(Short.parseShort(value));
                        orderRepository.save(order);
                    }
                } else if (type.equals("status")) {
                    Order order = orders.get(currentId - 1);
                    if (order != null) {
                        order.setStatus(value);
                        orderRepository.save(order);
                    }
                }
                break;
            default:
                break;
        }
    }
    
    
    @PreDestroy
    public void destroy() {
        System.out.println("Cleaning up resources");
    }
}
