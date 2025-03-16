package kg.alatoo.midterm.mappers;

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

    public static Seller toEntity(SellerDTO sellerDTO, List<User> users, List<Product> products) {
        if (sellerDTO == null) return null;
        return Seller.builder()
                .id(sellerDTO.getId())
                .name(sellerDTO.getName())
                .user(users)
                .product(products)
                .build();
    }
}
