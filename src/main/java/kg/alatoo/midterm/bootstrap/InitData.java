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
                String relatedEntityId = values.length > 3 ? values[3] : null;
                processLine(entity, type, value, relatedEntityId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Data creation from csv file is complete");
    }

    public void processLine(String entity, String type, String value, String relatedEntityId) {
        switch (entity) {
/////////////////////////////////////////////////////////////////////////////////////////////////
            case "Seller":
                if (type.equals("name")) {
                    Seller seller = Seller.builder()
                            .id(currentId++)
                            .name(value)
                            .user(new ArrayList<>())
                            .build();
                    sellers.put(seller.getId(), seller);
                    sellerRepository.save(seller);
                }
                else if (type.equals("userRelation") && relatedEntityId != null) {
                    Seller seller = sellers.get(currentId - 1);
                    User user = users.get(Long.parseLong(relatedEntityId));
                    if (seller != null && user != null) {
                        seller.getUser().add(user);
                        user.getSellers().add(seller);
                        sellerRepository.save(seller);
                        userRepository.save(user);
                    }
                }
                break;
/////////////////////////////////////////////////////////////////////////////////////////////////
            case "User":
                if (type.equals("name")) {
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
/////////////////////////////////////////////////////////////////////////////////////////////////
            case "Product":
                if (type.equals("name")) {
                    Seller productSeller = sellers.get(Long.parseLong(relatedEntityId));
                    Product product = Product.builder()
                            .id(currentId++)
                            .name(value)
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
                }
                break;
            default:
                break;
/////////////////////////////////////////////////////////////////////////////////////////////////
        }
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Cleaning up resources");
    }
}
