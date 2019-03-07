package com.buyanywhere.productcatalog.controllers.v1;

import com.buyanywhere.productcatalog.Services.ICategoriesService;
import com.buyanywhere.productcatalog.dto.CategoryDto;
import com.buyanywhere.productcatalog.exceptions.CategoryNotFoundException;
import com.buyanywhere.productcatalog.exceptions.CategoryNotValidException;
import com.buyanywhere.productcatalog.models.Category;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/v1/category")
public class CategoryController extends BaseController {
    private ICategoriesService service;

    public CategoryController(ICategoriesService service, ModelMapper mapper) {
        super(mapper);
        this.service = service;
    }

    @GetMapping(value = "/{id}")
    public CategoryDto get(@PathVariable long id) throws CategoryNotFoundException{
        if(!service.exists(id)){
            throw new CategoryNotFoundException(id);
        }

        Category category = service.findById(id);

        return mapper.map(category, CategoryDto.class);
    }

    @PostMapping
    public CategoryDto post(@RequestBody CategoryDto categoryDto) throws CategoryNotValidException{
        if(!categoryDto.isValid()){
            throw new CategoryNotValidException(this.getInvalidFields(categoryDto));
        }

        Category category = mapper.map(categoryDto, Category.class);

        category.setName(categoryDto.getName().trim());

        return mapper.map(service.add(category), CategoryDto.class);
    }

    @PutMapping(value = "/{id}")
    public CategoryDto put(@PathVariable long id, @RequestBody CategoryDto categoryDto)
            throws CategoryNotValidException, CategoryNotFoundException {
        if(!service.exists(id)){
            throw new CategoryNotFoundException(id);
        }

        if(!categoryDto.isValid()){
            throw new CategoryNotValidException(this.getInvalidFields(categoryDto));
        }

        Category category = service.findById(id);

        category.setName(categoryDto.getName().trim());
        category.setDisplayOrder(categoryDto.getDisplayOrder());

        return mapper.map(service.update(category), CategoryDto.class);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable long id) throws CategoryNotFoundException{
        if(!service.exists(id)){
            throw new CategoryNotFoundException(id);
        }

        Category category = service.findById(id);

        category.delete();

        service.update(category);
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