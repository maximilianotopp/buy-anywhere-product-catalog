package com.buyanywhere.productcatalog.controllers.v1;

import com.buyanywhere.productcatalog.dto.CategoryDto;
import com.buyanywhere.productcatalog.exceptions.CategoryNotFoundException;
import com.buyanywhere.productcatalog.exceptions.CategoryNotValidException;
import com.buyanywhere.productcatalog.exceptions.DuplicatedCategoryException;
import com.buyanywhere.productcatalog.models.Category;
import com.buyanywhere.productcatalog.services.CategoriesService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.FieldSetter;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.notNull;

@RunWith(SpringRunner.class)
public class CategoryControllerTests {

    @MockBean
    private CategoriesService service;

    @Test
    public void get_whenExists_shouldReturnCategory(){
        Category category = new Category("Home", 1);
        MockExists(1, true);
        MockFindById(1, category);

        CategoryController controller = new CategoryController(service, new ModelMapper());
        CategoryDto dto = controller.get(1);

        Assert.assertEquals("Home", dto.getName());
        Assert.assertEquals(1, dto.getDisplayOrder());
    }

    @Test(expected = CategoryNotFoundException.class)
    public void get_whenNotExists_shouldReturnCategory(){
        MockExists(2, false);

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
        dto.setName("InvalidDisplayOrder");
        dto.setDisplayOrder(-1);

        controller.post(dto);
    }

    @Test(expected = CategoryNotValidException.class)
    public void post_whenNameIsNullAndDisplayOrderIsNotValid_shouldReturnException(){
        CategoryController controller = new CategoryController(service, new ModelMapper());

        CategoryDto dto = new CategoryDto();
        dto.setDisplayOrder(-1);

        controller.post(dto);
    }

    @Test(expected = CategoryNotValidException.class)
    public void post_whenNameIsEmptyAndDisplayOrderIsNotValid_shouldReturnException(){
        CategoryController controller = new CategoryController(service, new ModelMapper());

        CategoryDto dto = new CategoryDto();
        dto.setName("");
        dto.setDisplayOrder(-1);

        controller.post(dto);
    }

    @Test(expected = DuplicatedCategoryException.class)
    public void post_whenNameIsDuplicated_shouldReturnException(){
        final String name = "DuplicatedName";
        MockIsDuplicated(name, true);
        CategoryController controller = new CategoryController(service, new ModelMapper());

        CategoryDto dto = new CategoryDto();
        dto.setName(name);

        controller.post(dto);
    }

    @Test
    public void post_whenNameIsNotDuplicatedAndValid_shouldReturnCategory(){
        final String name = "NewCategory";
        final int displayOrder = 5;
        MockIsDuplicated(name, false);

        CategoryDto dto = new CategoryDto();
        dto.setName(name);
        dto.setDisplayOrder(displayOrder);

        Category category = new Category(name, displayOrder);
        SetField(category, "id", (long) 1);

        MockAdd(category);

        CategoryController controller = new CategoryController(service, new ModelMapper());

        CategoryDto resultDto = controller.post(dto);

        Assert.assertEquals((long)1, (long)resultDto.getId());
        Assert.assertEquals(name, resultDto.getName());
        Assert.assertEquals(displayOrder, resultDto.getDisplayOrder());
    }

    @Test(expected = CategoryNotFoundException.class)
    public void put_whenNotExists_shouldReturnException(){
        final String name = "NotExists";
        MockExists(1, false);

        CategoryController controller = new CategoryController(service, new ModelMapper());

        CategoryDto dto = new CategoryDto();
        dto.setName(name);

        controller.put(1, dto);
    }

    @Test(expected = CategoryNotValidException.class)
    public void put_whenNameIsNull_shouldReturnException(){
        MockExists(1, true);
        CategoryController controller = new CategoryController(service, new ModelMapper());

        controller.put(1, new CategoryDto());
    }

    @Test(expected = CategoryNotValidException.class)
    public void put_whenNameIsEmpty_shouldReturnException(){
        MockExists(1, true);
        CategoryController controller = new CategoryController(service, new ModelMapper());

        CategoryDto dto = new CategoryDto();
        dto.setName("");

        controller.put(1, dto);
    }

    @Test(expected = CategoryNotValidException.class)
    public void put_whenDisplayOrderIsNotValid_shouldReturnException(){
        MockExists(1, true);
        CategoryController controller = new CategoryController(service, new ModelMapper());

        CategoryDto dto = new CategoryDto();
        dto.setName("InvalidDisplayOrder");
        dto.setDisplayOrder(-1);

        controller.put(1, dto);
    }

    @Test(expected = CategoryNotValidException.class)
    public void put_whenNameIsNullAndDisplayOrderIsNotValid_shouldReturnException(){
        MockExists(1, true);
        CategoryController controller = new CategoryController(service, new ModelMapper());

        CategoryDto dto = new CategoryDto();
        dto.setDisplayOrder(-1);

        controller.put(1, dto);
    }

    @Test(expected = CategoryNotValidException.class)
    public void put_whenNameIsEmptyAndDisplayOrderIsNotValid_shouldReturnException(){
        MockExists(1, true);
        CategoryController controller = new CategoryController(service, new ModelMapper());

        CategoryDto dto = new CategoryDto();
        dto.setName("");
        dto.setDisplayOrder(-1);

        controller.put(1, dto);
    }

    @Test(expected = DuplicatedCategoryException.class)
    public void put_whenNameIsDuplicated_shouldReturnException(){
        final String name = "DuplicatedName";
        MockExists(1, true);
        MockIsDuplicated(1, name, true);
        CategoryController controller = new CategoryController(service, new ModelMapper());

        CategoryDto dto = new CategoryDto();
        dto.setName(name);

        controller.put(1, dto);
    }

    @Test
    public void put_whenExistsAndNameIsNotDuplicatedAndValid_shouldReturnCategory(){
        final String name = "Electronics";
        final int displayOrder = 7;
        MockExists(1, true);
        MockIsDuplicated(1, name, false);

        CategoryController controller = new CategoryController(service, new ModelMapper());

        CategoryDto dto = new CategoryDto();
        dto.setName(name);
        dto.setDisplayOrder(displayOrder);

        Category category = new Category(name, displayOrder);
        SetField(category, "id", (long)1);

        MockUpdate(category);
        MockFindById(1, category);

        CategoryDto resultDto = controller.put(1, dto);

        Assert.assertEquals((long) 1, (long)resultDto.getId());
        Assert.assertEquals(name, resultDto.getName());
        Assert.assertEquals(displayOrder, resultDto.getDisplayOrder());
    }

    @Test(expected = CategoryNotFoundException.class)
    public void delete_whenNotExistingId_shouldReturnException(){
        MockExists(1, false);
        CategoryController controller = new CategoryController(service, new ModelMapper());

        controller.delete(1);
    }

    @Test
    public void delete_whenExistingId_shouldReturnCategory(){
        MockExists(1, true);
        CategoryController controller = new CategoryController(service, new ModelMapper());

        Category category = new Category("Name", 1);

        MockFindById(1, category);
        controller.delete(1);

        Assert.assertTrue(category.isDeleted());
        Mockito.verify(service, Mockito.atLeastOnce()).update(notNull());
    }

    private void MockExists(long id, boolean value){
        Mockito.when(service.exists(id)).thenReturn(value);
    }

    private void MockFindById(long id, Category returnCategory){
        Mockito.when(service.findById(id)).thenReturn(returnCategory);
    }

    private void MockIsDuplicated(String name, boolean value){
        Mockito.when(service.isDuplicated(name)).thenReturn(value);
    }

    private void MockIsDuplicated(long id, String name, boolean value){
        Mockito.when(service.isDuplicated(id, name)).thenReturn(value);
    }

    private void MockAdd(Category category){
        Mockito.when(service.add(notNull())).thenReturn(category);
    }

    private void MockUpdate(Category category){
        Mockito.when(service.update(notNull())).thenReturn(category);
    }

    private void SetField(Category category, String fieldName, Object value){
        try {
            FieldSetter.setField(category, category.getClass().getDeclaredField(fieldName), value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
