package kg.alatoo.midterm.services.implementation;

import kg.alatoo.midterm.dtos.SellerDTO;
import kg.alatoo.midterm.entities.Product;
import kg.alatoo.midterm.entities.Seller;
import kg.alatoo.midterm.entities.User;
import kg.alatoo.midterm.exceptions.ResourceNotFoundException;
import kg.alatoo.midterm.mappers.SellerMapper;
import kg.alatoo.midterm.repositories.ProductRepository;
import kg.alatoo.midterm.repositories.SellerRepository;
import kg.alatoo.midterm.repositories.UserRepository;
import kg.alatoo.midterm.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Seller createSeller(SellerDTO sellerDTO) {
        if (sellerDTO == null) {
            throw new IllegalArgumentException("SellerDTO cannot be null.");
        }

        List<User> users = new ArrayList<>();
        if (sellerDTO.getUserIds() != null) {
            for (Long userId : sellerDTO.getUserIds()) {
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
                users.add(user);
            }
        }

        List<Product> products = new ArrayList<>();
        if (sellerDTO.getProductIds() != null) {
            for (Long productId : sellerDTO.getProductIds()) {
                Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
                products.add(product);
            }
        }

        Seller seller = SellerMapper.toEntity(sellerDTO);
        seller.setUser(users);
        seller.setProduct(products);
        return sellerRepository.save(seller);
    }

    @Override
    public Seller getSellerById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Seller ID cannot be null.");
        }

        return sellerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Seller not found with id: " + id));
    }

    @Override
    public List<Seller> getAllSellers() {
        List<Seller> sellers = sellerRepository.findAll();
        if (sellers.isEmpty()) {
            throw new ResourceNotFoundException("No sellers found.");
        }
        return sellers;
    }

    @Override
    public Seller updateSeller(Long id, SellerDTO sellerDTO) {
        if (id == null || sellerDTO == null) {
            throw new IllegalArgumentException("Seller ID and SellerDTO cannot be null.");
        }

        Seller existingSeller = getSellerById(id);

        List<User> users = new ArrayList<>();
        if (sellerDTO.getUserIds() != null) {
            for (Long userId : sellerDTO.getUserIds()) {
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
                users.add(user);
            }
        }

        List<Product> products = new ArrayList<>();
        if (sellerDTO.getProductIds() != null) {
            for (Long productId : sellerDTO.getProductIds()) {
                Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
                products.add(product);
            }
        }

        existingSeller.setName(sellerDTO.getName());
        existingSeller.setUser(users);
        existingSeller.setProduct(products);
        return sellerRepository.save(existingSeller);
    }

    @Override
    public void deleteSeller(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Seller ID cannot be null.");
        }

        if (!sellerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Seller not found with id: " + id);
        }

        sellerRepository.deleteById(id);
    }
}
