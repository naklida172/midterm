package kg.alatoo.midterm.mappers;

import java.util.ArrayList;
import java.util.List;

import kg.alatoo.midterm.dtos.UserDTO;
import kg.alatoo.midterm.entities.Seller;
import kg.alatoo.midterm.entities.User;

public class UserMapper {
    public static UserDTO toDTO(User user) {
        if (user == null) return null;
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .role(user.getRole())
                .email(user.getEmail())
                .phone(user.getPhone())
                .sellerIds(user.getSellers() != null
                        ? user.getSellers().stream().map(Seller::getId).toList()
                        : null)
                .build();
    }

    public static User toEntity(UserDTO userDTO) {
        if (userDTO == null) return null;

        List<Seller> sellers = new ArrayList<>();
        if (userDTO.getSellerIds() != null) {
            for (Long sellerId : userDTO.getSellerIds()) {
                sellers.add(Seller.builder().id(sellerId).build());
            }
        }

        return User.builder()
                .name(userDTO.getName())
                .username(userDTO.getUsername())
                .role(userDTO.getRole())
                .email(userDTO.getEmail())
                .phone(userDTO.getPhone())
                .sellers(sellers)
                .build();
    }
}
