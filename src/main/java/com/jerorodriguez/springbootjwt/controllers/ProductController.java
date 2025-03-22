package com.jerorodriguez.springbootjwt.controllers;

import com.jerorodriguez.springbootjwt.entities.Product;
import com.jerorodriguez.springbootjwt.services.ProductService;
import com.jerorodriguez.springbootjwt.entities.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")  // Prefijo de la API
// Eliminar la anotación @CrossOrigin para usar la configuración centralizada
public class ProductController {
    final private ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

//    @GetMapping("/")
//    public ResponseEntity<List<Product>> list() {
//        return ResponseEntity.ok(service.findAll());
//    }

    @GetMapping("/")
    public ResponseEntity<List<Product>> list(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(service.findByUser(currentUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> details(@PathVariable Long id) {
        Optional<Product> optionalProduct = service.findById(id);
        if (optionalProduct.isPresent()) {
            return ResponseEntity.ok(optionalProduct.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<Product> create(@RequestBody Product product, @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(product, currentUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product,
                                          @AuthenticationPrincipal User currentUser) {
        Optional<Product> optionalProduct = service.findById(id);
        if (optionalProduct.isPresent()) {
            Product productDb = optionalProduct.orElseThrow();
            productDb.setName(product.getName());
            productDb.setDescription(product.getDescription());
            productDb.setPrice(product.getPrice());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(productDb, currentUser));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> delete(@PathVariable Long id) {
        Optional<Product> optionalProduct = service.deleteById(id);
        if (optionalProduct.isPresent()) {
            Product productDeleted = optionalProduct.orElseThrow();
            return ResponseEntity.status(HttpStatus.OK).body(productDeleted);
        }
        return ResponseEntity.notFound().build();
    }
}