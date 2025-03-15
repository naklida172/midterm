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
    try (BufferedReader br = new BufferedReader(new FileReader("init-data.csv"))) {
        String line;
        while ((line = br.readLine()) != null) {
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

    public void processLine(String entity, String type, String value, String relatedEntityId){

    }

    @PreDestroy
    public void destroy() {
        System.out.println("Cleaning up resources");
    }
}
