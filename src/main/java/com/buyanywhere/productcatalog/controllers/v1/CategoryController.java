package com.buyanywhere.productcatalog.controllers.v1;

import com.buyanywhere.productcatalog.models.Category;
import com.buyanywhere.productcatalog.repositories.CategoryRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;


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
    public Category post(@RequestBody Category category) {return repository.save(category);}
}
