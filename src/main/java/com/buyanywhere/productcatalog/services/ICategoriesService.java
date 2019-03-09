package com.buyanywhere.productcatalog.services;

import com.buyanywhere.productcatalog.exceptions.CategoryNotFoundException;
import com.buyanywhere.productcatalog.models.Category;
import org.springframework.data.jpa.domain.Specification;
import java.util.List;

public interface ICategoriesService {
    Category findById(Long id) throws CategoryNotFoundException;
    boolean exists(Long id);
    boolean isDuplicated(String name);
    boolean isDuplicated(Long id, String name);
    List<Category> findBySpecification(Specification<Category> specification);
    Category add(Category category);
    Category update(Category category);
}
