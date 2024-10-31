package com.beautyclaws.beautyclaws.api;

import com.beautyclaws.beautyclaws.db.Product;
import com.beautyclaws.beautyclaws.db.Productsrepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/myapi/products")

public class ProductsController {
    private Productsrepository productsrepository;
    public ProductsController(Productsrepository productsrepository) {
        this.productsrepository = productsrepository;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllproducts(){
        List<Product> products = productsrepository.findAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProducts(@RequestBody CreateProductsRequest request){
    Product product = new Product();
    product.setName(request.getName());
   product.setDescription(request.getDescription());
   product.setPrice(request.getPrice());
   product.setStock(request.getStock());
   Product savedProduct = productsrepository.saveAndFlush(product);

        return new ResponseEntity<>(savedProduct, HttpStatus.OK);
    }
}