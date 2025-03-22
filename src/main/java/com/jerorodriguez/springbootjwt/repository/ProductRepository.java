package com.jerorodriguez.springbootjwt.repository;

import com.jerorodriguez.springbootjwt.entities.Product;
import com.jerorodriguez.springbootjwt.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findByCreatedBy(User currentUser);
}

