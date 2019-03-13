package com.buyanywhere.productcatalog.services;

import com.buyanywhere.productcatalog.exceptions.CategoryNotFoundException;
import com.buyanywhere.productcatalog.models.Category;
import com.buyanywhere.productcatalog.repositories.ICategoryRepository;
import org.springframework.data.jpa.domain.Specification;
import java.util.List;
import java.util.Optional;

public class CategoriesService implements ICategoriesService {
    private ICategoryRepository repository;

    public CategoriesService(ICategoryRepository repository){
        this.repository = repository;
    }

    @Override
    public Category findById(Long id) throws CategoryNotFoundException {
        Optional<Category> category = repository.findById(id);

        if(!category.isPresent() || category.get().isDeleted()){
            throw new CategoryNotFoundException(id);
        }

        return category.get();
    }

    @Override
    public boolean exists(Long id){
        Optional<Category> categoryOptional = repository.findById(id);
        return categoryOptional.isPresent() && !categoryOptional.get().isDeleted();
    }

    @Override
    public boolean isDuplicated(String name) {
        return !repository.findByNameAndDeletedFalse(name).isEmpty();
    }

    @Override
    public boolean isDuplicated(Long id, String name) {
        List<Category> matchingCategories = repository.findByNameAndDeletedFalse(name);

        return !matchingCategories.isEmpty() && matchingCategories.get(0).getId() != id;
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
