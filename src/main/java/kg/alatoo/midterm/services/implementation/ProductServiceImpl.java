package kg.alatoo.midterm.services.implementation;

import kg.alatoo.midterm.dtos.ProductDTO;
import kg.alatoo.midterm.entities.Product;
import kg.alatoo.midterm.entities.Seller;
import kg.alatoo.midterm.entities.Tag;
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
        Seller seller = sellerRepository.findById(productDTO.getSellerId())
                .orElseThrow(() -> new RuntimeException("Seller not found with id: " + productDTO.getSellerId()));

        List<Tag> tags = new ArrayList<>();
        if (productDTO.getTagIds() != null) {
            for (Long tagId : productDTO.getTagIds()) {
                Tag tag = tagRepository.findById(tagId)
                        .orElseThrow(() -> new RuntimeException("Tag not found with id: " + tagId));
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
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product updateProduct(Long id, ProductDTO productDTO) {
        Product existingProduct = getProductById(id);

        Seller seller = sellerRepository.findById(productDTO.getSellerId())
                .orElseThrow(() -> new RuntimeException("Seller not found with id: " + productDTO.getSellerId()));

        List<Tag> tags = new ArrayList<>();
        if (productDTO.getTagIds() != null) {
            for (Long tagId : productDTO.getTagIds()) {
                Tag tag = tagRepository.findById(tagId)
                        .orElseThrow(() -> new RuntimeException("Tag not found with id: " + tagId));
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
        productRepository.deleteById(id);
    }
}
