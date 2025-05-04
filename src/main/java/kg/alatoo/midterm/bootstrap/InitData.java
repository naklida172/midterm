package kg.alatoo.midterm.bootstrap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.transaction.Transactional;
import kg.alatoo.midterm.entities.Order;
import kg.alatoo.midterm.entities.Point;
import kg.alatoo.midterm.entities.Product;
import kg.alatoo.midterm.entities.Seller;
import kg.alatoo.midterm.entities.Tag;
import kg.alatoo.midterm.entities.User;
import kg.alatoo.midterm.repositories.OrderRepository;
import kg.alatoo.midterm.repositories.PointRepository;
import kg.alatoo.midterm.repositories.ProductRepository;
import kg.alatoo.midterm.repositories.SellerRepository;
import kg.alatoo.midterm.repositories.TagRepository;
import kg.alatoo.midterm.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
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

    private Long currentId = null;

    private final List<DeferredRelationship> deferredRelationships = new ArrayList<>();

    @PostConstruct
    @Transactional
    public void init() {
        log.info("Creating values from src\\main\\java\\kg\\alatoo\\midterm\\bootstrap\\InitData.csv");
        try (BufferedReader csValues = new BufferedReader(
                new FileReader("src\\main\\java\\kg\\alatoo\\midterm\\bootstrap\\InitData.csv"))) {
            String line;
            while ((line = csValues.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length < 3)
                    continue;
                String entity = values[0];
                String type = values[1];
                String value = values[2];
                processLine(entity, type, value);
            }
            processDeferredRelationships();
        } catch (IOException e) {
            log.error("Error reading the CSV file", e);
        }

        log.info("Data creation from CSV file is complete");
    }

    public void processLine(String entity, String type, String value) {
        log.info("Processing entity: " + entity + ", type: " + type + ", value: " + value);
        switch (entity) {
            case "Seller":
                processSeller(type, value);
                break;
            case "User":
                processUser(type, value);
                break;
            case "Product":
                processProduct(type, value);
                break;
            case "Tag":
                processTag(type, value);
                break;
            case "Point":
                processPoint(type, value);
                break;
            case "Order":
                processOrder(type, value);
                break;
            default:
                log.warn("Unknown entity type: " + entity);
        }
    }

    private void processSeller(String type, String value) {
        switch (type) {
            case ("name"):
                Seller seller = Seller.builder()
                        .name(value)
                        .user(new ArrayList<>())
                        .build();
                seller = sellerRepository.save(seller);
                currentId = seller.getId();
                sellers.put(seller.getId(), seller);
                break;
            case ("userRelation"):
                Seller sellerForRelations = sellers.get(currentId);
                String[] userIdStrings = value.split(";");
                List<Long> userIds = new ArrayList<>();
                for (String userIdIter : userIdStrings) {
                    try {
                        userIds.add(Long.parseLong(userIdIter.trim()));
                    } catch (NumberFormatException e) {
                        log.error("Invalid user ID format: " + userIdIter, e);
                    }
                }

                deferredRelationships
                        .add(new DeferredRelationship(sellerForRelations.getId(), userIds, "Seller", "User"));
                break;
            default:
                log.warn("Unknown product type: " + type);
        }
    }

    private void processUser(String type, String value) {
        switch (type) {
            case "name":
                User user = User.builder()
                        .name(value)
                        .username(null)
                        .role(null)
                        .email(null)
                        .phone(null)
                        .sellers(new ArrayList<>())
                        .build();
                user = userRepository.save(user);
                users.put(user.getId(), user);
                currentId = user.getId();
                break;

                case "username":
                if (currentId != null) {
                    User userForUsername = users.get(currentId);
                    if (userForUsername != null) {
                        userForUsername.setUsername(value);
                        userRepository.save(userForUsername);
                    }
                }
                break;

                case "password":
                if(currentId != null) {
                    User userForPassword = users.get(currentId);
                    if (userForPassword != null) {
                        userForPassword.setPassword(value);
                        userRepository.save(userForPassword);
                    }
                }
    
            case "role":
                if (currentId != null) {
                    User userForRole = users.get(currentId);
                    if (userForRole != null) {
                        userForRole.setRole(value);
                        userRepository.save(userForRole);
                    }
                }
                break;
    
            case "email":
                if (currentId != null) {
                    User userForEmail = users.get(currentId);
                    if (userForEmail != null) {
                        userForEmail.setEmail(value);
                        userRepository.save(userForEmail);
                    }
                }
                break;
    
            case "phone":
                if (currentId != null) {
                    User userForPhone = users.get(currentId);
                    if (userForPhone != null) {
                        userForPhone.setPhone(value);
                        userRepository.save(userForPhone);
                    }
                }
                break;
    
            default:
                log.warn("Unknown user type: " + type);
        }
    }

    private void processProduct(String type, String value) {
        switch (type) {
            case "name":
                Product product = Product.builder()
                        .name(value)
                        .description(null)
                        .rating((short) 0)
                        .seller(null)
                        .tags(new ArrayList<>())
                        .build();
                product = productRepository.save(product);
                currentId = product.getId();
                products.put(product.getId(), product);
                break;

            case "description":
                Product productForDescription = products.get(currentId);
                if (productForDescription != null) {
                    productForDescription.setDescription(value);
                    productRepository.save(productForDescription);
                }
                break;

            case "rating":
                Product productForRating = products.get(currentId);
                if (productForRating != null) {
                    productForRating.setRating(Short.parseShort(value));
                    productRepository.save(productForRating);
                }
                break;

            case "sellerRelation":
                Product productForSeller = products.get(currentId);
                String[] selleIdStrings = value.split(";");
                List<Long> selleIds = new ArrayList<>();
                for (String tagIdIter : selleIdStrings) {
                    try {
                        selleIds.add(Long.parseLong(tagIdIter.trim()));
                    } catch (NumberFormatException e) {
                        log.error("Invalid tag ID format: " + tagIdIter, e);
                    }
                }
                deferredRelationships
                        .add(new DeferredRelationship(productForSeller.getId(), selleIds, "Product", "Seller"));
                break;

            case "tagRelation":
                Product productForTag = products.get(currentId);

                String[] tagIdStrings = value.split(";");
                List<Long> tagIds = new ArrayList<>();
                for (String tagIdIter : tagIdStrings) {
                    try {
                        tagIds.add(Long.parseLong(tagIdIter.trim()));
                    } catch (NumberFormatException e) {
                        log.error("Invalid tag ID format: " + tagIdIter, e);
                    }
                }
                deferredRelationships.add(new DeferredRelationship(productForTag.getId(), tagIds, "Product", "Tag"));
                break;
            default:
                log.warn("Unknown product type: " + type);
        }
    }

    private void processTag(String type, String value) {
        switch (type) {
            case "name":
                Tag tag = Tag.builder()
                        .name(value)
                        .description(null)
                        .products(new ArrayList<>())
                        .build();
                tag = tagRepository.save(tag);
                currentId = tag.getId();
                tags.put(tag.getId(), tag);
                break;

            case "description":
                Tag tagForDescription = tags.get(currentId);
                if (tagForDescription != null) {
                    tagForDescription.setDescription(value);
                    tagRepository.save(tagForDescription);
                }
                break;
            default:
                log.warn("Unknown tag type: " + type);
        }
    }

    private void processPoint(String type, String value) {
        switch (type) {
            case "address":
                Point point = Point.builder()
                        .address(value)
                        .status(null)
                        .workTime(null)
                        .orders(new ArrayList<>())
                        .build();
                point = pointRepository.save(point);
                currentId = point.getId();
                points.put(point.getId(), point);
                break;

            case "status":
                Point pointForStatus = points.get(currentId);
                if (pointForStatus != null) {
                    pointForStatus.setStatus(value);
                    pointRepository.save(pointForStatus);
                }
                break;

            case "workTime":
                Point pointForWorkTime = points.get(currentId);
                if (pointForWorkTime != null) {
                    pointForWorkTime.setWorkTime(value);
                    pointRepository.save(pointForWorkTime);
                }
                break;

            default:
                log.warn("Unknown point type: " + type);
        }
    }

    private void processOrder(String type, String value) {
        switch (type) {
            case "orderDate":
                Order order = Order.builder()
                        .orderDate(new Date())
                        .quantity((short) 0)
                        .status("")
                        .user(null)
                        .product(null)
                        .point(null)
                        .build();
                order = orderRepository.save(order);
                currentId = order.getId();
                orders.put(order.getId(), order);
                break;
            case "quantity":
                Order orderForQuantity = orders.get(currentId);
                if (orderForQuantity != null) {
                    orderForQuantity.setQuantity(Short.parseShort(value));
                    orderRepository.save(orderForQuantity);
                }
                break;
            case "status":
                Order orderForStatus = orders.get(currentId);
                if (orderForStatus != null) {
                    orderForStatus.setStatus(value);
                    orderRepository.save(orderForStatus);
                }
                break;
            case "productRelation":
                Order orderForProductRelation = orders.get(currentId);
                Long productId;
                try {
                    productId = Long.parseLong(value.trim());
                    deferredRelationships.add(new DeferredRelationship(orderForProductRelation.getId(),
                            List.of(productId), "Order", "Product"));
                } catch (NumberFormatException e) {
                    log.error("Invalid product ID format: " + value, e);
                }
                break;
            case "pointRelation":
                Order orderForPointRelation = orders.get(currentId);

                Long pointId;
                try {
                    pointId = Long.parseLong(value.trim());
                    deferredRelationships.add(new DeferredRelationship(orderForPointRelation.getId(), List.of(pointId),
                            "Order", "Point"));
                } catch (NumberFormatException e) {
                    log.error("Invalid point ID format: " + value, e);
                }
                break;
            case "userRelation":
                Order orderForUserRelation = orders.get(currentId);

                Long userId;
                try {
                    userId = Long.parseLong(value.trim());
                    deferredRelationships.add(
                            new DeferredRelationship(orderForUserRelation.getId(), List.of(userId), "Order", "User"));
                } catch (NumberFormatException e) {
                    log.error("Invalid user ID format: " + value, e);
                }
                break;
            default:
                log.warn("Unknown order type: " + type);
        }
    }

    private Object fetchEntityByTypeAndId(String entityType, Long id) {
        switch (entityType) {
            case "Seller":
                return sellerRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException(entityType + " with ID " + id + " not found"));
            case "User":
                return userRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException(entityType + " with ID " + id + " not found"));
            case "Product":
                return productRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException(entityType + " with ID " + id + " not found"));
            case "Tag":
                return tagRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException(entityType + " with ID " + id + " not found"));
            case "Point":
                return pointRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException(entityType + " with ID " + id + " not found"));
            case "Order":
                return orderRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException(entityType + " with ID " + id + " not found"));
            default:
                throw new RuntimeException("Unknown entity type: " + entityType);
        }
    }

    private void saveEntity(Object entity, String entityType) {
        switch (entityType) {
            case "Seller":
                sellerRepository.save((Seller) entity);
                break;
            case "User":
                userRepository.save((User) entity);
                break;
            case "Product":
                productRepository.save((Product) entity);
                break;
            case "Tag":
                tagRepository.save((Tag) entity);
                break;
            case "Point":
                pointRepository.save((Point) entity);
                break;
            case "Order":
                orderRepository.save((Order) entity);
                break;
            default:
                throw new RuntimeException("Unknown entity type: " + entityType);
        }
    }

    private void linkEntities(Object mainEntity, Object relatedEntity, String mainEntityType, String relationType) {
        try {
            switch (mainEntityType) {
                case "Seller":
                    if ("User".equals(relationType)) {
                        Seller seller = (Seller) mainEntity;
                        User user = (User) relatedEntity;
                        seller.getUser().add(user);
                        user.getSellers().add(seller);
                    }
                    break;

                case "Product":
                    if ("Seller".equals(relationType)) {
                        Product product = (Product) mainEntity;
                        Seller seller = (Seller) relatedEntity;
                        product.setSeller(seller);
                        seller.getProduct().add(product);
                    } else if ("Tag".equals(relationType)) {
                        Product product = (Product) mainEntity;
                        Tag tag = (Tag) relatedEntity;
                        product.getTags().add(tag);
                        tag.getProducts().add(product);
                    }
                    break;

                case "Order":
                    if ("Product".equals(relationType)) {
                        Order order = (Order) mainEntity;
                        Product product = (Product) relatedEntity;
                        order.setProduct(product);
                    } else if ("Point".equals(relationType)) {
                        Order order = (Order) mainEntity;
                        Point point = (Point) relatedEntity;
                        order.setPoint(point);
                        point.getOrders().add(order);
                    } else if ("User".equals(relationType)) {
                        Order order = (Order) mainEntity;
                        User user = (User) relatedEntity;
                        order.setUser(user);
                    }
                    break;

                default:
                    log.warn("Unknown relationship type between mainEntityType: " + mainEntityType
                            + " and relationType: " + relationType);
            }
        } catch (Exception e) {
            log.error("Error linking deferred relationship", e);
        }
    }

    private void processDeferredRelationships() {
        for (DeferredRelationship relationship : deferredRelationships) {
            try {
                Long mainId = relationship.getMainId();
                List<Long> relationIds = relationship.getRelationId();
                String mainEntityType = relationship.getMainEntityType();
                String relationType = relationship.getRelationEntityType();
                Object mainEntity = fetchEntityByTypeAndId(mainEntityType, mainId);
                log.info("Processing deferred relationship: mainEntityType = " + mainEntityType +
                        ", mainId = " + mainId + ", relationType = " + relationType + ", relationIds = " + relationIds);
                for (Long relationId : relationIds) {
                    Object relatedEntity = fetchEntityByTypeAndId(relationType, relationId);
                    log.info("fetchEntityByTypeAndId successfull");
                    linkEntities(mainEntity, relatedEntity, mainEntityType, relationType);
                    log.info("linkEntities successfull");
                }
                saveEntity(mainEntity, mainEntityType);
            } catch (Exception e) {
                log.error("Error processing deferred relationship for main ID: " + relationship.getMainId(), e);
            }
        }

        log.info("Deferred relationships processed.");
    }

    @PreDestroy
    public void destroy() {
        log.info("Cleaning up resources");
    }
}
