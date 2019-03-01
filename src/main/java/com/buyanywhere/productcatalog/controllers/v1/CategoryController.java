package com.buyanywhere.productcatalog.controllers.v1;

import com.buyanywhere.productcatalog.dto.CategoryDto;
import com.buyanywhere.productcatalog.exceptions.CategoryNotFoundException;
import com.buyanywhere.productcatalog.exceptions.CategoryNotValidException;
import com.buyanywhere.productcatalog.exceptions.DuplicatedCategoryException;
import com.buyanywhere.productcatalog.models.Category;
import com.buyanywhere.productcatalog.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/category")
public class CategoryController extends BaseController {
    private CategoryRepository repository;

    public CategoryController(CategoryRepository repository, ModelMapper mapper) {
        super(mapper);
        this.repository = repository;
    }

    @GetMapping(value = "/{id}")
    public CategoryDto get(@PathVariable long id) throws CategoryNotFoundException{
        if(!exists(id)){
            throw new CategoryNotFoundException(id);
        }

        Category category = repository.findById(id).get();

        return mapper.map(category, CategoryDto.class);
    }

    @PostMapping
    public CategoryDto post(@RequestBody CategoryDto categoryDto) throws CategoryNotValidException{
        if(!categoryDto.isValid()){
            throw new CategoryNotValidException(this.getInvalidFields(categoryDto));
        }

        String categoryDtoName = categoryDto.getName().trim();

        if(!repository.findByNameAndDeletedFalse(categoryDtoName).isEmpty()) {
            throw new DuplicatedCategoryException(categoryDtoName);
        }

        Category category = mapper.map(categoryDto, Category.class);

        category.setName(categoryDtoName);

        return mapper.map(repository.save(category), CategoryDto.class);
    }

    @PutMapping(value = "/{id}")
    public CategoryDto put(@PathVariable long id, @RequestBody CategoryDto categoryDto)
            throws CategoryNotValidException, CategoryNotFoundException {
        if(!exists(id)){
            throw new CategoryNotFoundException(id);
        }

        if(!categoryDto.isValid()){
            throw new CategoryNotValidException(this.getInvalidFields(categoryDto));
        }

        String categoryDtoName = categoryDto.getName().trim();

        List<Category> matchingCategories = repository.findByNameAndDeletedFalse(categoryDtoName);

        if(!matchingCategories.isEmpty() && matchingCategories.get(0).getId() != id) {
            throw new DuplicatedCategoryException(categoryDtoName);
        }

        Category category = repository.findById(id).get();

        category.setName(categoryDtoName);
        category.setDisplayOrder(categoryDto.getDisplayOrder());

        return mapper.map(repository.save(category), CategoryDto.class);
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

    private String getInvalidFields(CategoryDto categoryDto){
        String invalidFields = new String();

        String name = categoryDto.getName();

        if(name == null || name.trim().isEmpty()){
            invalidFields = "name";
        }

        if(categoryDto.getDisplayOrder() < 0){
            invalidFields = invalidFields.trim().isEmpty()
                    ? "displayOrder"
                    : invalidFields + ", displayOrder";
        }

        return invalidFields;
    }
}