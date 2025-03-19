package kg.alatoo.midterm.mappers;

import java.util.ArrayList;
import java.util.List;

import kg.alatoo.midterm.dtos.SellerDTO;
import kg.alatoo.midterm.entities.Product;
import kg.alatoo.midterm.entities.Seller;
import kg.alatoo.midterm.entities.User;

public class SellerMapper {
    public static SellerDTO toDTO(Seller seller) {
        if (seller == null) return null;
        return SellerDTO.builder()
                .id(seller.getId())
                .name(seller.getName())
                .userIds(seller.getUser() != null
                        ? seller.getUser().stream().map(User::getId).toList()
                        : null)
                .productIds(seller.getProduct() != null ? seller.getProduct().stream().map(Product::getId).toList()
                : null)
                .build();
    }

    public static Seller toEntity(SellerDTO sellerDTO) {
    if (sellerDTO == null) return null;

    List<User> users = new ArrayList<>();
    if (sellerDTO.getUserIds() != null) {
        for (Long userId : sellerDTO.getUserIds()) {
            users.add(User.builder().id(userId).build());
        }
    }

    List<Product> products = new ArrayList<>();
    if (sellerDTO.getProductIds() != null) {
        for (Long productId : sellerDTO.getProductIds()) {
            products.add(Product.builder().id(productId).build());
        }
    }

    return Seller.builder()
            .name(sellerDTO.getName())
            .user(users)
            .product(products)
            .build();
    }
}
