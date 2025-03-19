package kg.alatoo.midterm.mappers;

import java.util.ArrayList;
import java.util.List;

import kg.alatoo.midterm.dtos.TagDTO;
import kg.alatoo.midterm.entities.Product;
import kg.alatoo.midterm.entities.Tag;

public class TagMapper {
    public static TagDTO toDTO(Tag tag) {
        if (tag == null) return null;
        return TagDTO.builder()
                .id(tag.getId())
                .name(tag.getName())
                .description(tag.getDescription())
                .productIds(tag.getProducts() != null
                        ? tag.getProducts().stream().map(Product::getId).toList()
                        : null)
                .build();
    }

    public static Tag toEntity(TagDTO tagDTO) {
        if (tagDTO == null) return null;

        List<Product> products = new ArrayList<>();
        if (tagDTO.getProductIds() != null) {
            for (Long productId : tagDTO.getProductIds()) {
                products.add(Product.builder().id(productId).build());
            }
        }

        return Tag.builder()
                .name(tagDTO.getName())
                .description(tagDTO.getDescription())
                .products(products)
                .build();
    }
}
