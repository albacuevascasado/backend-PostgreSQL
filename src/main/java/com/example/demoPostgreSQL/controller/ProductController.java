package com.example.demoPostgreSQL.controller;

import com.example.demoPostgreSQL.model.Product;
import com.example.demoPostgreSQL.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public ResponseEntity getAllProducts() {
        return ResponseEntity.ok(this.productRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity getOneProduct(@PathVariable Long id) {
        return  ResponseEntity.ok(this.productRepository.findById(id).get());
    }

    @PostMapping
    public ResponseEntity createProduct(@RequestBody Product product) {
        return ResponseEntity.status(201).body(this.productRepository.save(product));
    }

    @PutMapping ("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Product productToUpdate = this.productRepository.findById(id).get();
        if(product.getName() != "") {
            productToUpdate.setName(product.getName());
        }

        if(product.getDescription() != "") {
            productToUpdate.setDescription(product.getDescription());
        }

        this.productRepository.save(productToUpdate);
        return productToUpdate;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable Long id) {
        Product productDeleted = this.productRepository.findById(id).get();
        this.productRepository.deleteById(id);
        return  ResponseEntity.ok("This product has been deleted: "+ productDeleted.getName() );

    }

}
