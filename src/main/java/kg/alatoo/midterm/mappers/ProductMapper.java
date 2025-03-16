package kg.alatoo.midterm.mappers;

import java.util.ArrayList;
import java.util.List;

import kg.alatoo.midterm.dtos.ProductDTO;
import kg.alatoo.midterm.entities.Product;
import kg.alatoo.midterm.entities.Seller;
import kg.alatoo.midterm.entities.Tag;

public class ProductMapper {
    public static ProductDTO toDTO(Product product) {
        if (product == null) return null;
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .rating(product.getRating())
                .sellerId(product.getSeller() != null ? product.getSeller().getId() : null)
                .tagIds(product.getTags() != null
                        ? product.getTags().stream().map(Tag::getId).toList()
                        : null)
                .build();
    }

    public static Product toEntity(ProductDTO productDTO) {
        if (productDTO == null) return null;
    
        Seller seller = productDTO.getSellerId() != null ? Seller.builder().id(productDTO.getSellerId()).build() : null;
        List<Tag> tags = new ArrayList<>();
        if (productDTO.getTagIds() != null) {
            for (Long tagId : productDTO.getTagIds()) {
                tags.add(Tag.builder().id(tagId).build());
            }
        }
    
        return Product.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .rating(productDTO.getRating())
                .seller(seller)
                .tags(tags)
                .build();
    }
}
