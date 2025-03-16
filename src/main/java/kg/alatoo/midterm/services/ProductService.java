package kg.alatoo.midterm.services;

import kg.alatoo.midterm.dtos.ProductDTO;
import kg.alatoo.midterm.entities.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(ProductDTO productDTO);
    Product getProductById(Long id);
    List<Product> getAllProducts();
    Product updateProduct(Long id, ProductDTO productDTO);
    void deleteProduct(Long id);
}
