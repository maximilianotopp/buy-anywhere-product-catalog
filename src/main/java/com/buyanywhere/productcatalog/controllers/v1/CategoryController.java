package com.buyanywhere.productcatalog.controllers.v1;

import com.buyanywhere.productcatalog.dto.CategoryDto;
import com.buyanywhere.productcatalog.exceptions.CategoryNotFoundException;
import com.buyanywhere.productcatalog.exceptions.CategoryNotValidException;
import com.buyanywhere.productcatalog.exceptions.DuplicatedCategoryException;
import com.buyanywhere.productcatalog.models.Category;
import com.buyanywhere.productcatalog.repositories.ICategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/v1/category")
public class CategoryController extends BaseController {
    private ICategoryRepository repository;

    public CategoryController(ICategoryRepository repository, ModelMapper mapper) {
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

        Category category = mapper.map(categoryDto, Category.class);

        String categoryDtoName = categoryDto.getName().trim();

        if(findByName(categoryDtoName).isPresent()){
            throw new DuplicatedCategoryException(categoryDtoName);
        }

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

        Optional<Category> optionalCategory = findByName(categoryDtoName);

        if(optionalCategory.isPresent() && optionalCategory.get().getId() != id){
            throw new DuplicatedCategoryException(categoryDtoName);
        }

        Category category = optionalCategory.get();

        category.setName(categoryDto.getName().trim());
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

    private Optional<Category> findByName(String name){
        Specification<Category> specification = Specification
                .where((root, criteriaQuery, criteriaBuilder) ->
                        criteriaBuilder.equal(criteriaBuilder.lower(root.get("name")), name));

        specification = specification.and((root, criteriaQuery, criteriaBuilder) ->
                            criteriaBuilder.equal(root.<String>get("deleted"), 0));

        return repository.findOne(specification);
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