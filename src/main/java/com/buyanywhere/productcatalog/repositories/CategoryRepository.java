package com.buyanywhere.productcatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.buyanywhere.productcatalog.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}