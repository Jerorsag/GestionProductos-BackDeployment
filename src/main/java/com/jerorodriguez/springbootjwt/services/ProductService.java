package com.jerorodriguez.springbootjwt.services;

import com.jerorodriguez.springbootjwt.entities.Product;
import com.jerorodriguez.springbootjwt.entities.User;

import java.util.List;
import java.util.Optional;

public interface ProductService {
//    List<Product> findAll();
    List<Product> findByUser(User user);
    Optional<Product> findById(Long id);
    Product save(Product product, User currentUser);  // Modificado para incluir el usuario
    Optional<Product> deleteById(Long id);
}

