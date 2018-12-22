package com.buyanywhere.productcatalog.controllers.v1;

import com.buyanywhere.productcatalog.models.Category;
import com.buyanywhere.productcatalog.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/category")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/add"
    )
    public @ResponseBody String addNewCategory (@RequestParam String name,
                                                @RequestParam int displayOrder){
        Category category = new Category();
        category.setName(name);
        category.setDisplayOrder(displayOrder);
        categoryRepository.save(category);
        return "saved";
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Category> getAllCategories(){
        return categoryRepository.findAll();
    }
}