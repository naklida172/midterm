package kg.alatoo.midterm.services.implementation;

import kg.alatoo.midterm.dtos.ProductDTO;
import kg.alatoo.midterm.entities.Product;
import kg.alatoo.midterm.entities.Seller;
import kg.alatoo.midterm.entities.Tag;
import kg.alatoo.midterm.exceptions.ResourceNotFoundException;
import kg.alatoo.midterm.mappers.ProductMapper;
import kg.alatoo.midterm.repositories.ProductRepository;
import kg.alatoo.midterm.repositories.SellerRepository;
import kg.alatoo.midterm.repositories.TagRepository;
import kg.alatoo.midterm.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private TagRepository tagRepository;

    @Override
    public Product createProduct(ProductDTO productDTO) {
        if (productDTO == null) {
            throw new IllegalArgumentException("ProductDTO cannot be null.");
        }

        Seller seller = sellerRepository.findById(productDTO.getSellerId())
                .orElseThrow(() -> new ResourceNotFoundException("Seller not found with id: " + productDTO.getSellerId()));

        List<Tag> tags = new ArrayList<>();
        if (productDTO.getTagIds() != null) {
            for (Long tagId : productDTO.getTagIds()) {
                Tag tag = tagRepository.findById(tagId)
                        .orElseThrow(() -> new ResourceNotFoundException("Tag not found with id: " + tagId));
                tags.add(tag);
            }
        }

        Product product = ProductMapper.toEntity(productDTO);
        product.setSeller(seller);
        product.setTags(tags);
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Product ID cannot be null.");
        }

        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No products found.");
        }
        return products;
    }

    @Override
    public Product updateProduct(Long id, ProductDTO productDTO) {
        if (id == null || productDTO == null) {
            throw new IllegalArgumentException("Product ID and ProductDTO cannot be null.");
        }

        Product existingProduct = getProductById(id);

        Seller seller = sellerRepository.findById(productDTO.getSellerId())
                .orElseThrow(() -> new ResourceNotFoundException("Seller not found with id: " + productDTO.getSellerId()));

        List<Tag> tags = new ArrayList<>();
        if (productDTO.getTagIds() != null) {
            for (Long tagId : productDTO.getTagIds()) {
                Tag tag = tagRepository.findById(tagId)
                        .orElseThrow(() -> new ResourceNotFoundException("Tag not found with id: " + tagId));
                tags.add(tag);
            }
        }

        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setRating(productDTO.getRating());
        existingProduct.setSeller(seller);
        existingProduct.setTags(tags);
        return productRepository.save(existingProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Product ID cannot be null.");
        }

        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }

        productRepository.deleteById(id);
    }
}
