package com.buyanywhere.productcatalog.controllers.v1;

import com.buyanywhere.productcatalog.models.Category;
import com.buyanywhere.productcatalog.repositories.CategoryRepository;
import com.buyanywhere.productcatalog.specifications.CategorySpecificationsBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/v1/categories")
public class CategoriesController {
    private CategoryRepository repository;

    public CategoriesController(CategoryRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(
            method = RequestMethod.GET
    )
    public List<Category> get(@RequestParam(value = "filterBy") String filterBy,
                              @RequestParam(value = "showDeleted", defaultValue = "false") boolean showDeleted){

        Specification<Category> spec = Specification.where((root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.like(
                    root.<String>get("name"), "%" + filterBy + "%");
        }).and((root, criteriaQuery, criteriaBuilder) -> {

        });
        return repository.findAll();
    }
}
