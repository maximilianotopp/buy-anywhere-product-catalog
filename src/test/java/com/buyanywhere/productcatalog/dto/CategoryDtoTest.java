package com.buyanywhere.productcatalog.dto;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class CategoryDtoTest {

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
    public void isValid_whenNameAndDisplayOrderAreValid_shouldReturnTrue(){
        CategoryDto dto = new CategoryDto();
        dto.setName("Home");
        dto.setDisplayOrder(1);

        Assert.assertTrue(dto.isValid());
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
    public void getInvalidFields_whenDisplayOrderIsNotValid_shouldReturnDisplayOrder(){
        CategoryDto dto = new CategoryDto();
        dto.setName("Home");
        dto.setDisplayOrder(-2);

        Assert.assertEquals(dto.getInvalidFields(), "displayOrder");
    }

    @Test
    public void getInvalidFields_whenNameAndDisplayOrderAreNotValid_shouldReturnNameAndDisplayOrder(){
        CategoryDto dto = new CategoryDto();
        dto.setName("");
        dto.setDisplayOrder(-2);

        Assert.assertEquals(dto.getInvalidFields(), "name, displayOrder");
    }

    @Test
    public void getInvalidFields_whenNameAndDisplayOrderAreValid_shouldReturnAnEmptyString(){
        CategoryDto dto = new CategoryDto();
        dto.setName("NameAndDisplayOrderValid");
        dto.setDisplayOrder(2);

        Assert.assertEquals(dto.getInvalidFields(), "");
    }
}
