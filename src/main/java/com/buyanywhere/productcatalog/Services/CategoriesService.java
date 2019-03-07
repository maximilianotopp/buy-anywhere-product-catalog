package com.buyanywhere.productcatalog.Services;

import com.buyanywhere.productcatalog.models.Category;
import com.buyanywhere.productcatalog.repositories.CategoryRepository;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public class CategoriesService implements ICategoriesService {
    private CategoryRepository repository;

    public CategoriesService(CategoryRepository repository){
        this.repository = repository;
    }

    public Category findById(Long id){
        return repository.findById(id).get();
    }

    public boolean exists(Long id){
        Optional<Category> categoryOptional = repository.findById(id);
        return categoryOptional.isPresent() && !categoryOptional.get().isDeleted();
    }

    @Override
    public List<Category> findBySpecification(Specification<Category> specification){
        return repository.findAll(specification);
    }

    @Override
    public Category add(Category category){
        return repository.save(category);
    }

    @Override
    public Category update(Category category) {
        return repository.save(category);
    }
}
