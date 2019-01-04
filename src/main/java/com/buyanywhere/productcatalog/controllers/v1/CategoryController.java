package com.buyanywhere.productcatalog.controllers.v1;

import com.buyanywhere.productcatalog.exceptions.ArgumentNotValidException;
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
            method=RequestMethod.GET,
            value="/{id}"
    )
    public Category get(@PathVariable("id") long id) {
        return repository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/"
    )
    public Category post(@RequestBody Category data){
        return repository.save(data);
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/"
    )
    public Category put(@RequestBody Category data){
        if (!exist(data.getId())) throw new CategoryNotFoundException(data.getId());
        if(data.getName().isEmpty()) throw new ArgumentNotValidException("name");
        if(data.getDisplayOrder() < 0) throw new ArgumentNotValidException("displayOrder");
        data.setName(data.getName().trim());
        return repository.save(data);
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/{id}"
    )
    public Category delete(@PathVariable("id") long id){
        if (!exist(id))  throw new CategoryNotFoundException(id);
        Category category = repository.findById(id).get();
        category.setDeleted(true);
        return repository.save(category);
    }

    private boolean exist (long id){
        return repository.findById(id).isPresent() && !repository.findById(id).get().isDeleted();
    }
}