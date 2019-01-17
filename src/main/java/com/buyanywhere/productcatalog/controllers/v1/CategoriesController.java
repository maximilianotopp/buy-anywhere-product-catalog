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
            method = RequestMethod.GET,
            value = "/"
    )
    public List<Category> get(@RequestParam(value = "search") String search){
        CategorySpecificationsBuilder builder = new CategorySpecificationsBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)(:)(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }

        Specification<Category> spec = builder.build();
        return repository.findAll(spec);
    }
}
