package com.jerorodriguez.springbootjwt.services;

import com.jerorodriguez.springbootjwt.entities.Product;
import com.jerorodriguez.springbootjwt.repository.ProductRepository;
import com.jerorodriguez.springbootjwt.entities.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    final private ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

//    @Transactional(readOnly = true)
//    @Override
//    public List<Product> findAll() {
//        return (List<Product>) repository.findAll();
//    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> findByUser(User currentUser) {
        return repository.findByCreatedBy(currentUser);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Product save(Product product, User currentUser) {
        // Asignar el usuario actual como creador del producto
        product.setCreatedBy(currentUser);
        return repository.save(product);
    }

    @Override
    @Transactional
    public Optional<Product> deleteById(Long id) {
        Optional<Product> productOptional = repository.findById(id);
        if (productOptional.isPresent()) {
            repository.deleteById(id);
            return productOptional;
        }
        return Optional.empty();
    }
}