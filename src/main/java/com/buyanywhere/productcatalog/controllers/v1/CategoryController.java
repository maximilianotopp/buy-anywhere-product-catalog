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
            value = "/"
    )
    public Category post(@RequestBody Category data){
        return repository.save(data);
    }

    @RequestMapping(
            method=RequestMethod.GET,
            value="/{id}"
    )
    public Category findCategoty(@PathVariable("id") long id) {
        Category category = repository.findCategoryById(id);
        if (category == null) throw new CategoryNotFoundException();
        return category;
    }
}