package com.rizalamar.momsbakery.repository;

import com.rizalamar.momsbakery.domain.Category;
import com.rizalamar.momsbakery.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Optional<Product> findProductById(UUID id);

    List<Product> findAll();

    boolean existsProductByName(String name);
}
