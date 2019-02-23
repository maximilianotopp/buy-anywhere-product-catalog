package com.buyanywhere.productcatalog.controllers.v1;

import com.buyanywhere.productcatalog.dto.CategoryDto;
import com.buyanywhere.productcatalog.exceptions.CategoryNotFoundException;
import com.buyanywhere.productcatalog.exceptions.CategoryNotValidException;
import com.buyanywhere.productcatalog.models.Category;
import com.buyanywhere.productcatalog.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/v1/category")
public class CategoryController {
    private CategoryRepository repository;

    public CategoryController(CategoryRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/{id}")
    public Category get(@PathVariable long id) throws CategoryNotFoundException{
        if(!exists(id)){
            throw new CategoryNotFoundException(id);
        }

        Category category = repository.findById(id).get();

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.createTypeMap(Category.class, CategoryDto.class);

        CategoryDto categoryDto = new CategoryDto();

        modelMapper.map(category, categoryDto);

        return category;
    }

    @PostMapping
    public Category post(@RequestBody Category category) throws CategoryNotValidException{
        if(!category.isValid()){
            throw new CategoryNotValidException(this.getInvalidFields(category));
        }

        return repository.save(category);
    }

    @PutMapping
    public Category put(@RequestBody Category category) throws CategoryNotValidException, CategoryNotFoundException {
        if(!category.isValid()){
            throw new CategoryNotValidException(this.getInvalidFields(category));
        }

        if(!exists(category.getId())){
            throw new CategoryNotFoundException(category.getId());
        }

        return repository.save(category);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable long id) throws CategoryNotFoundException{
        if(!exists(id)){
            throw new CategoryNotFoundException(id);
        }

        Category category = repository.findById(id).get();

        category.delete();

        repository.save(category);
    }

    private boolean exists(long id){
        Optional<Category> categoryOptional = repository.findById(id);

        return categoryOptional.isPresent() && !categoryOptional.get().isDeleted();
    }

    private String getInvalidFields(Category category){
        String invalidFields = new String();
        if(category.getName().trim().isEmpty()){
            invalidFields = "name";
        }

        if(category.getDisplayOrder() < 0){
            invalidFields = invalidFields.trim().isEmpty()
                    ? "displayOrder"
                    : invalidFields + ", displayOrder";
        }

        return invalidFields;
    }
}