package com.rizalamar.momsbakery.repository;

import com.rizalamar.momsbakery.domain.Category;
import com.rizalamar.momsbakery.domain.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Optional<Category> findCategoriesById(UUID id);

    List<Category> findAll();

    boolean existsCategoriesByName(CategoryType name);
}
