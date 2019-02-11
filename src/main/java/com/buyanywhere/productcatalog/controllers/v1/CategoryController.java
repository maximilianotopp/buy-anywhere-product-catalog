package com.buyanywhere.productcatalog.controllers.v1;

import com.buyanywhere.productcatalog.exceptions.CategoryNotFoundException;
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

    @RequestMapping(method = RequestMethod.POST, value = "/create-new")
    public Category post(@RequestBody Category category) {
        return repository.save(category);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/get/{id}")
    @ResponseBody
    public Category get(@PathVariable("id") long id) throws CategoryNotFoundException {

        if ((repository.findById(id).get().isDeleted() == false) && (repository.existsById(id))) {
            return repository.findById(id).get();
        } else {
            throw new CategoryNotFoundException("No category with Id: <" + Long.toString(id) + ">");
        }
    }
}