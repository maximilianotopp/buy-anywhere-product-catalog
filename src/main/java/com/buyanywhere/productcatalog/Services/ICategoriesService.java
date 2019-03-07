package com.buyanywhere.productcatalog.Services;

import com.buyanywhere.productcatalog.models.Category;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ICategoriesService {
    Category findById(Long id);
    boolean exists(Long id);
    boolean isDuplicated(String name);
    boolean isDuplicated(Long id, String name);
    List<Category> findBySpecification(Specification<Category> specification);
    Category add(Category category);
    Category update(Category category);
}