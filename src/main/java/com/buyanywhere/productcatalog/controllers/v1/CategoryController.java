package com.buyanywhere.productcatalog.controllers.v1;

import com.buyanywhere.productcatalog.exceptions.CategoryNotFoundException;
import com.buyanywhere.productcatalog.models.Category;
import com.buyanywhere.productcatalog.repositories.CategoryRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/category")
public class CategoryController {
    private CategoryRepository repository;

    public CategoryController(CategoryRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/create"
    )
    public Category create(@RequestBody Category data){
        return repository.save(data);
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/update"
    )
    public Category update(@RequestBody Category data){
        return repository.findById(data.getId()).map(category -> {
            category.setName(data.getName());
            category.setDisplayOrder(data.getDisplayOrder());
            return repository.save(category);
        })
                .orElseThrow(() -> new CategoryNotFoundException(data.getId()));
    }

    @RequestMapping(
            method=RequestMethod.GET,
            value="/{id}"
    )
    public Category get(@PathVariable("id") long id) {
        return repository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
    }
}