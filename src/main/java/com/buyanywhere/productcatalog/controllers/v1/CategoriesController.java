package com.buyanywhere.productcatalog.controllers.v1;

import com.buyanywhere.productcatalog.exceptions.ArgumentNotValidException;
import com.buyanywhere.productcatalog.models.Category;
import com.buyanywhere.productcatalog.repositories.CategoryRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;
import java.util.*;


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
                              @RequestParam(value = "showDeleted", defaultValue = "false") boolean showDeleted,
                              @RequestParam(value = "orderBy", defaultValue = "alpha") String orderBy,
                              @RequestParam(value = "reverse", defaultValue = "false") boolean reverse){

        Specification<Category> spec;

        if(!(orderBy.equals("alpha") || orderBy.equals("order"))) {
            throw new ArgumentNotValidException("orderBy");
        }

        if (filterBy.length() > 3){
            spec = Specification.where((root, criteriaQuery, criteriaBuilder) -> {
                return criteriaBuilder.like(
                        root.<String>get("name"), "%" + filterBy + "%");
            });
        } else {
            spec = Specification.where((root, criteriaQuery, criteriaBuilder) -> {
                return criteriaBuilder.like(
                        root.<String>get("name"), "%");
            });
        }

        if (!showDeleted) {
            spec = Specification.where(spec).and((root, criteriaQuery, criteriaBuilder) -> {
                return criteriaBuilder.equal(
                        root.<String>get("deleted"), 0);
            });
        }

        List<Category> searchResults = repository.findAll(spec);

        Comparator<Category> categoryComparator;
        if(orderBy.equals("order")){
            categoryComparator = Comparator.comparing(Category::getDisplayOrder);
        } else {
            categoryComparator = Comparator.comparing(Category::getName);
        }

        if(reverse){
            Collections.sort(searchResults, categoryComparator.reversed());
        } else {
            Collections.sort(searchResults, categoryComparator);
        }

        return searchResults;
    }
}
