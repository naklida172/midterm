package kg.alatoo.midterm.mappers;

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

    public static Tag toEntity(TagDTO tagDTO, List<Product> products) {
        if (tagDTO == null) return null;
        return Tag.builder()
                .id(tagDTO.getId())
                .name(tagDTO.getName())
                .description(tagDTO.getDescription())
                .products(products)
                .build();
    }
}
