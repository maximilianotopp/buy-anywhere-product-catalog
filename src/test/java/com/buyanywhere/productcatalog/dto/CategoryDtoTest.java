package com.buyanywhere.productcatalog.dto;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class CategoryDtoTest {

    private CategoryDto categoryDto;

    @Before
    public void setup(){
        categoryDto = new CategoryDto();
        categoryDto.setId((long) 1);
        categoryDto.setName("Home");
        categoryDto.setDisplayOrder(1);
    }

    @Test
    public void getId_shouldReturnId(){
        Assert.assertTrue(this.categoryDto.getId() == 1);
    }

    @Test
    public void getName_shouldReturnName(){
        Assert.assertEquals(this.categoryDto.getName(), "Home");
    }

    @Test
    public void getDisplayOrder_shouldReturnDisplayOrder(){
        Assert.assertEquals(this.categoryDto.getDisplayOrder(), 1);
    }

    @Test
    public void isValid_whenNameIsNull_shouldReturnFalse(){
        CategoryDto dto = new CategoryDto();

        Assert.assertFalse(dto.isValid());
    }

    @Test
    public void isValid_whenNameIsEmpty_shouldReturnFalse(){
        CategoryDto dto = new CategoryDto();
        dto.setName("");

        Assert.assertFalse(dto.isValid());
    }

    @Test
    public void isValid_whenNameIsValidAndDisplayOrderIsNotValid_shouldReturnFalse(){
        CategoryDto dto = new CategoryDto();
        dto.setName("DisplayOrderIsNotValid");
        dto.setDisplayOrder(-1);

        Assert.assertFalse(dto.isValid());
    }

    @Test
    public void isValid_whenNameAndDisplayOrderAreValid_shouldReturnFalse(){
        Assert.assertTrue(this.categoryDto.isValid());
    }

    @Test
    public void getInvalidFields_whenNameIsNull_shouldReturnName(){
        CategoryDto dto = new CategoryDto();
        dto.setDisplayOrder(2);

        Assert.assertEquals(dto.getInvalidFields(), "name");
    }

    @Test
    public void getInvalidFields_whenNameIsEmpty_shouldReturnName(){
        CategoryDto dto = new CategoryDto();
        dto.setName("");
        dto.setDisplayOrder(2);

        Assert.assertEquals(dto.getInvalidFields(), "name");
    }

    @Test
    public void getInvalidFields_whenDisplayOrderIsNotValid_shouldReturnName(){
        CategoryDto dto = new CategoryDto();
        dto.setName("Home");
        dto.setDisplayOrder(-2);

        Assert.assertEquals(dto.getInvalidFields(), "displayOrder");
    }

    @Test
    public void getInvalidFields_whenNameAndDisplayOrderAreNotValid_shouldReturnName(){
        CategoryDto dto = new CategoryDto();
        dto.setName("");
        dto.setDisplayOrder(-2);

        Assert.assertEquals(dto.getInvalidFields(), "name, displayOrder");
    }
}
