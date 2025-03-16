package kg.alatoo.midterm.services;

import kg.alatoo.midterm.dtos.SellerDTO;
import kg.alatoo.midterm.entities.Seller;

import java.util.List;

public interface SellerService {
    Seller createSeller(SellerDTO sellerDTO);
    Seller getSellerById(Long id);
    List<Seller> getAllSellers();
    Seller updateSeller(Long id, SellerDTO sellerDTO);
    void deleteSeller(Long id);
}
