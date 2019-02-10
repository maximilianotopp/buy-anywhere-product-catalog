package com.buyanywhere.productcatalog.controllers.v1;

import com.buyanywhere.productcatalog.exceptions.CategoryNotFoundException;
import com.buyanywhere.productcatalog.exceptions.CategoryNotValidException;
import com.buyanywhere.productcatalog.models.Category;
import com.buyanywhere.productcatalog.repositories.CategoryRepository;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/v1/category")
public class CategoryController {
    private CategoryRepository repository;

    public CategoryController(CategoryRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Category get(@PathVariable long id){
        if(!exists(id)){
            throw new CategoryNotFoundException(id);
        }

        return repository.findById(id).get();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/")
    public Category post(@RequestBody Category data){
        if(!data.isValid()){
            throw new CategoryNotValidException(data.invalidValue());
        }

        return repository.save(data);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/")
    public Category put(@RequestBody Category category){
        if(!category.isValid()){
            throw new CategoryNotValidException(category.invalidValue());
        }

        if(!exists(category.getId())){
            throw new CategoryNotFoundException(category.getId());
        }

        return repository.save(category);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public void delete(@PathVariable long id){
        if(!exists(id)){
            throw new CategoryNotFoundException(id);
        }

        Category category = repository.findById(id).get();
        category.delete();

        repository.save(category);
    }

    private boolean exists(long id){
        Optional<Category> categoryOptional = repository.findById(id);

        return categoryOptional.isPresent() && !categoryOptional.get().isDeleted();
    }
}