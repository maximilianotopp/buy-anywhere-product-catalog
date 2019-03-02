package com.buyanywhere.productcatalog.controllers.v1;

import com.buyanywhere.productcatalog.exceptions.ArgumentNotValidException;
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
    public Category get(@PathVariable Long id) throws CategoryNotFoundException {
        if(!exists(id)) {
            throw new CategoryNotFoundException(id);
        }

        return repository.findById(id).get();
    }

    @PostMapping
    public Category post(@RequestBody Category category){
        return repository.save(category);
    }

    @PutMapping
    private Category update(@RequestBody Category category) throws CategoryNotFoundException, ArgumentNotValidException{
       if(!exists(category.getId())) {
           throw new CategoryNotFoundException(category.getId());
       }

       if (category.getName().isEmpty()) throw new ArgumentNotValidException("name");

       if (category.getDisplayOrder() < 0) throw new ArgumentNotValidException("displayOrder");

       return repository.save(category);
    }

    private boolean exists(long id){
        Optional<Category> categoryOptional = repository.findById(id);

        return categoryOptional.isPresent() && !categoryOptional.get().isDeleted();
    }
}