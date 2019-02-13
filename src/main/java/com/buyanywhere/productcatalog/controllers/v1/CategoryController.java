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
        if (!repository.findById(id).isPresent() || repository.findById(id).get().isDeleted()) {
            throw new CategoryNotFoundException(id);
        }

        return repository.findById(id).get();
    }

    @PostMapping
    public Category post(@RequestBody Category category) {
        return repository.save(category);
    }

    @PutMapping
    public Category put(@RequestBody Category category) throws InvalidCategoryException {
        if (!category.isValid()) {
            throw new InvalidCategoryException(category.showInformation());
        }

        return repository.save(category);
    }
}