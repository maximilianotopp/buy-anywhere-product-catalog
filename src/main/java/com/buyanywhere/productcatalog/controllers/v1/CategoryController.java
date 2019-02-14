package com.buyanywhere.productcatalog.controllers.v1;

import com.buyanywhere.productcatalog.exceptions.CategoryNotFoundException;
import com.buyanywhere.productcatalog.exceptions.InvalidCategoryException;
import org.springframework.web.bind.annotation.*;
import com.buyanywhere.productcatalog.models.Category;
import com.buyanywhere.productcatalog.repositories.CategoryRepository;

@RestController
@RequestMapping("/v1/category")

public class CategoryController {

    private CategoryRepository repository;

    public CategoryController(CategoryRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/{id}")
    public Category get(@PathVariable("id") long id) throws CategoryNotFoundException {
        if (!exist(id)) {
            throw new CategoryNotFoundException(id);
        }

        return repository.findById(id).get();
    }

    @PostMapping
    public Category post(@RequestBody Category category) {
        return repository.save(category);
    }

    @PutMapping
    public Category put(@RequestBody Category category) throws InvalidCategoryException, CategoryNotFoundException {
        if (!category.isValid()) {
            throw new InvalidCategoryException(category.showInformation());
        }

        if (!exist(category.getId())) {
            throw new CategoryNotFoundException(category.getId());
        }

        return repository.save(category);
    }

    @DeleteMapping(value = "/{id}")
    public Category delete(@PathVariable long id) throws CategoryNotFoundException {
        if (!exist(id)) {
            throw new CategoryNotFoundException(id);
        }

        Category category = repository.findById(id).get();
        category.delete();
        return repository.save(category);
    }

    private boolean exist(long id) {
        return (repository.findById(id).isPresent() && !repository.findById(id).get().isDeleted());
    }
}