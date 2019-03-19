package com.buyanywhere.productcatalog.controllers.v1;

import com.buyanywhere.productcatalog.dto.CategoryDto;
import com.buyanywhere.productcatalog.exceptions.CategoryNotFoundException;
import com.buyanywhere.productcatalog.exceptions.CategoryNotValidException;
import com.buyanywhere.productcatalog.exceptions.DuplicatedCategoryException;
import com.buyanywhere.productcatalog.models.Category;
import com.buyanywhere.productcatalog.services.CategoriesService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.FieldSetter;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.notNull;

@RunWith(SpringRunner.class)
public class CategoryControllerTests {

    @MockBean
    private CategoriesService service;

    @Before
    public void setup(){
        List<Category> categories = new ArrayList<>();
        Category homeCategory = new Category("Home", 1);
        categories.add(homeCategory);

        Category newCategory = new Category("NewCategory", 5);
        categories.add(newCategory);

        Category updateCategory = new Category("UpdateCategory", 7);
        categories.add(updateCategory);

        Mockito.when(service.exists((long) 1)).thenReturn(true);
        Mockito.when(service.exists((long) 2)).thenReturn(false);
        Mockito.when(service.exists((long) 3)).thenReturn(true);
        Mockito.when(service.exists((long) 4)).thenReturn(true);

        Mockito.when(service.findById((long) 1)).thenReturn(categories.get(0));
        Mockito.when(service.findById((long) 3)).thenReturn(categories.get(2));

        Mockito.when(service.isDuplicated("DuplicatedName")).thenReturn(true);
        Mockito.when(service.isDuplicated(newCategory.getName())).thenReturn(false);

        Mockito.when(service.isDuplicated((long) 1, "DuplicatedName")).thenReturn(true);

        try {
            FieldSetter.setField(homeCategory, homeCategory.getClass().getDeclaredField("id"), (long) 1);
            FieldSetter.setField(newCategory, newCategory.getClass().getDeclaredField("id"), (long) 2);
            FieldSetter.setField(updateCategory, updateCategory.getClass().getDeclaredField("id"), (long) 3);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        Mockito.when(service.add(notNull())).thenReturn(categories.get(1));
        Mockito.when(service.update(notNull())).thenReturn(categories.get(2));
    }

    @Test
    public void get_whenExists_shouldReturnCategory(){
        CategoryController controller = new CategoryController(service, new ModelMapper());

        CategoryDto dto = controller.get(1);

        Assert.assertEquals("Home", dto.getName());
        Assert.assertEquals(1, dto.getDisplayOrder());
    }

    @Test(expected = CategoryNotFoundException.class)
    public void get_whenNotExists_shouldReturnCategory(){
        CategoryController controller = new CategoryController(service, new ModelMapper());

        controller.get(2);
    }

    @Test(expected = CategoryNotValidException.class)
    public void post_whenNameIsNull_shouldReturnException(){
        CategoryController controller = new CategoryController(service, new ModelMapper());

        controller.post(new CategoryDto());
    }

    @Test(expected = CategoryNotValidException.class)
    public void post_whenNameIsEmpty_shouldReturnException(){
        CategoryController controller = new CategoryController(service, new ModelMapper());

        CategoryDto dto = new CategoryDto();
        dto.setName("");

        controller.post(dto);
    }

    @Test(expected = CategoryNotValidException.class)
    public void post_whenDisplayOrderIsNotValid_shouldReturnException(){
        CategoryController controller = new CategoryController(service, new ModelMapper());

        CategoryDto dto = new CategoryDto();
        dto.setDisplayOrder(-1);

        controller.post(dto);
    }

    @Test(expected = DuplicatedCategoryException.class)
    public void post_whenNameIsDuplicated_shouldReturnException(){
        CategoryController controller = new CategoryController(service, new ModelMapper());

        CategoryDto dto = new CategoryDto();
        dto.setName("DuplicatedName");

        controller.post(dto);
    }

    @Test
    public void post_whenNameIsNotDuplicatedAndValid_shouldReturnCategory(){
        CategoryController controller = new CategoryController(service, new ModelMapper());

        CategoryDto dto = new CategoryDto();
        dto.setName("NewCategory");
        dto.setDisplayOrder(5);

        CategoryDto resultDto = controller.post(dto);

        Assert.assertNotNull(resultDto.getId());
        Assert.assertEquals(dto.getName(), resultDto.getName());
        Assert.assertEquals(dto.getDisplayOrder(), resultDto.getDisplayOrder());
    }

    @Test(expected = CategoryNotFoundException.class)
    public void put_whenNotExists_shouldReturnException(){
        CategoryController controller = new CategoryController(service, new ModelMapper());

        CategoryDto dto = new CategoryDto();
        dto.setName("NotExists");

        controller.put(2, dto);
    }

    @Test(expected = CategoryNotValidException.class)
    public void put_whenNameIsNull_shouldReturnException(){
        CategoryController controller = new CategoryController(service, new ModelMapper());

        controller.put(4, new CategoryDto());
    }

    @Test(expected = CategoryNotValidException.class)
    public void put_whenNameIsEmpty_shouldReturnException(){
        CategoryController controller = new CategoryController(service, new ModelMapper());

        CategoryDto dto = new CategoryDto();
        dto.setName("");

        controller.put(4, dto);
    }

    @Test(expected = CategoryNotValidException.class)
    public void put_whenDisplayOrderIsNotValid_shouldReturnException(){
        CategoryController controller = new CategoryController(service, new ModelMapper());

        CategoryDto dto = new CategoryDto();
        dto.setDisplayOrder(-1);

        controller.put(4, dto);
    }

    @Test(expected = DuplicatedCategoryException.class)
    public void put_whenNameIsDuplicated_shouldReturnException(){
        CategoryController controller = new CategoryController(service, new ModelMapper());

        CategoryDto dto = new CategoryDto();
        dto.setName("DuplicatedName");

        controller.put(1, dto);
    }

    @Test
    public void put_whenExistsAndNameIsNotDuplicatedAndValid_shouldReturnCategory(){
        CategoryController controller = new CategoryController(service, new ModelMapper());

        CategoryDto dto = new CategoryDto();
        dto.setName("Electronics");
        dto.setDisplayOrder(7);

        CategoryDto resultDto = controller.put(3, dto);

        Assert.assertNotNull(resultDto.getId());
        Assert.assertEquals(dto.getName(), resultDto.getName());
        Assert.assertEquals(dto.getDisplayOrder(), resultDto.getDisplayOrder());
    }

    @Test(expected = CategoryNotFoundException.class)
    public void delete_whenNotExistingId_shouldReturnException(){
        CategoryController controller = new CategoryController(service, new ModelMapper());

        controller.delete(2);
    }

    @Test
    public void delete_whenExistingId_shouldReturnCategory(){
        CategoryController controller = new CategoryController(service, new ModelMapper());

        controller.delete(3);
    }
}
