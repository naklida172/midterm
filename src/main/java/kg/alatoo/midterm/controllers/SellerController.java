package kg.alatoo.midterm.controllers;

import kg.alatoo.midterm.dtos.SellerDTO;
import kg.alatoo.midterm.entities.Seller;
import kg.alatoo.midterm.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sellers")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    @PostMapping
    public Seller createSeller(@RequestBody SellerDTO sellerDTO) {
        return sellerService.createSeller(sellerDTO);
    }

    @GetMapping("/{id}")
    public Seller getSellerById(@PathVariable Long id) {
        return sellerService.getSellerById(id);
    }

    @GetMapping
    public List<Seller> getAllSellers() {
        return sellerService.getAllSellers();
    }

    @PutMapping("/{id}")
    public Seller updateSeller(@PathVariable Long id, @RequestBody SellerDTO sellerDTO) {
        return sellerService.updateSeller(id, sellerDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteSeller(@PathVariable Long id) {
        sellerService.deleteSeller(id);
    }
}
