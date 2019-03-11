package com.buyanywhere.productcatalog.services;

import com.buyanywhere.productcatalog.exceptions.CategoryNotFoundException;
import com.buyanywhere.productcatalog.models.Category;
import com.buyanywhere.productcatalog.repositories.ICategoryRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
public class CategoriesServiceTest {
    @MockBean
    private ICategoryRepository categoryRepository;

    @Before
    public void setup(){
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("Home", 1));
        categories.add(new Category("Cars", 2));
        categories.add(new Category("Electronics", 3));
        categories.add(new Category("Computers", 4));

        Category deletedCategory = new Category("DeletedCategory", 5);
        deletedCategory.delete();
        categories.add(deletedCategory);

        Mockito.when(categoryRepository.findAll()).thenReturn(categories);
        Mockito.when(categoryRepository.findById((long) 1)).thenReturn(Optional.ofNullable(categories.get(0)));
        Mockito.when(categoryRepository.findById((long) 2)).thenReturn(Optional.ofNullable(categories.get(1)));
        Mockito.when(categoryRepository.findById((long) 3)).thenReturn(Optional.ofNullable(categories.get(2)));
        Mockito.when(categoryRepository.findById((long) 4)).thenReturn(Optional.ofNullable(categories.get(3)));
        Mockito.when(categoryRepository.findById((long) 5)).thenReturn(Optional.ofNullable(categories.get(4)));

        Mockito.when(categoryRepository.findByNameAndDeletedFalse("NewName")).thenReturn(new ArrayList<>());
        Mockito.when(categoryRepository.findByNameAndDeletedFalse("Cars")).thenReturn(new ArrayList<>(Collections.singleton(categories.get(1))));
        Mockito.when(categoryRepository.findByNameAndDeletedFalse("DeletedCategory")).thenReturn(new ArrayList<>());
    }

    @Test
    public void findById_whenExistingIdAndNotDeleted_shouldReturnCategory(){
        CategoriesService service = new CategoriesService(categoryRepository);

        Category category = service.findById((long) 3);

        Assert.assertEquals("Electronics", category.getName());
        Assert.assertEquals((long) 3, category.getDisplayOrder());
    }

    @Test(expected = CategoryNotFoundException.class)
    public void findById_whenExistingIdAndDeleted_shouldThrowCategoryNotFoundException(){
        CategoriesService service = new CategoriesService(categoryRepository);

        Category category = service.findById((long) 5);
    }
}
