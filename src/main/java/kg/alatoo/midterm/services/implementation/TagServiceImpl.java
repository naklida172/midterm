package kg.alatoo.midterm.services.implementation;

import kg.alatoo.midterm.dtos.TagDTO;
import kg.alatoo.midterm.entities.Product;
import kg.alatoo.midterm.entities.Tag;
import kg.alatoo.midterm.mappers.TagMapper;
import kg.alatoo.midterm.repositories.ProductRepository;
import kg.alatoo.midterm.repositories.TagRepository;
import kg.alatoo.midterm.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Tag createTag(TagDTO tagDTO) {
        List<Product> products = new ArrayList<>();
        if (tagDTO.getProductIds() != null) {
            for (Long productId : tagDTO.getProductIds()) {
                Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
                products.add(product);
            }
        }

        Tag tag = TagMapper.toEntity(tagDTO);
        tag.setProducts(products);
        return tagRepository.save(tag);
    }

    @Override
    public Tag getTagById(Long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found with id: " + id));
    }

    @Override
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public Tag updateTag(Long id, TagDTO tagDTO) {
        Tag existingTag = getTagById(id);

        List<Product> products = new ArrayList<>();
        if (tagDTO.getProductIds() != null) {
            for (Long productId : tagDTO.getProductIds()) {
                Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
                products.add(product);
            }
        }

        existingTag.setName(tagDTO.getName());
        existingTag.setDescription(tagDTO.getDescription());
        existingTag.setProducts(products);
        return tagRepository.save(existingTag);
    }

    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}
