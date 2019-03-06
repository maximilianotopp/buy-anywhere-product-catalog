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
    public CategoryController (CategoryRepository repository){
        this.repository = repository;
    }

    @RequestMapping(
        method = RequestMethod.POST,
        value = "/"
    )
    public Category post(@RequestBody Category category){
        return repository.save(category);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/{id}"
    )
    public Category getById(@PathVariable long id) throws CategoryNotFoundException {
        Optional<Category> categoryOptional = repository.findById(id);

        if(!categoryOptional.isPresent() || categoryOptional.get().isDeleted()){
            throw new CategoryNotFoundException(id);
        }

        return categoryOptional.get();
    }
}
