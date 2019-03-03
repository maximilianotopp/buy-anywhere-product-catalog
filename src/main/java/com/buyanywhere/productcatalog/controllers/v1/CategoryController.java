package com.buyanywhere.productcatalog.controllers.v1;

import com.buyanywhere.productcatalog.exceptions.CategoryNotFoundException;
import com.buyanywhere.productcatalog.models.Category;
import com.buyanywhere.productcatalog.repositories.CategoryRepository;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/v1/category")
public class CategoryController{
    private CategoryRepository repository;

    public CategoryController(CategoryRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/{id}")
    public Category get(@PathVariable long id) throws CategoryNotFoundException {
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
    public Category put(@RequestBody Category category) throws CategoryNotFoundException{
        long id = category.getId();
        if (!exist(id)) {
            throw new CategoryNotFoundException(id);
        }

        return repository.save(category);
    }

    @DeleteMapping(value = "/{id}")
    public Category delete(@PathVariable long id) throws CategoryNotFoundException {
        if (!exist(id)) {
            throw new CategoryNotFoundException(id);
        }

        Category category = repository.findById(id).get();
        category.setDeleted();
        return repository.save(category);
    }

    private boolean exist(long id){
        Optional<Category> categoryOptional = repository.findById(id);

        return categoryOptional.isPresent() && !categoryOptional.get().isDeleted();
    }
}