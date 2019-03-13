package com.buyanywhere.productcatalog.services;

import com.buyanywhere.productcatalog.exceptions.CategoryNotFoundException;
import com.buyanywhere.productcatalog.models.Category;
import com.buyanywhere.productcatalog.repositories.ICategoryRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.FieldSetter;
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

        Category notDeletedCategory = new Category("NotDeletedCategory", 6);
        categories.add(notDeletedCategory);

        Mockito.when(categoryRepository.findAll()).thenReturn(categories);
        Mockito.when(categoryRepository.findById((long) 1)).thenReturn(Optional.ofNullable(categories.get(0)));
        Mockito.when(categoryRepository.findById((long) 2)).thenReturn(Optional.ofNullable(categories.get(1)));
        Mockito.when(categoryRepository.findById((long) 3)).thenReturn(Optional.ofNullable(categories.get(2)));
        Mockito.when(categoryRepository.findById((long) 4)).thenReturn(Optional.ofNullable(categories.get(3)));
        Mockito.when(categoryRepository.findById((long) 5)).thenReturn(Optional.ofNullable(categories.get(4)));

        Mockito.when(categoryRepository.findByNameAndDeletedFalse("NewName")).thenReturn(new ArrayList<>());
        Mockito.when(categoryRepository.findByNameAndDeletedFalse("Cars")).thenReturn(new ArrayList<>(Collections.singleton(categories.get(1))));
        Mockito.when(categoryRepository.findByNameAndDeletedFalse("DeletedCategory")).thenReturn(new ArrayList<>());

        Mockito.when(categoryRepository.findByNameAndDeletedFalse("NotDeletedCategory")).thenReturn(new ArrayList<>(Collections.singleton(categories.get(5))));

        try {
            FieldSetter.setField(notDeletedCategory, notDeletedCategory.getClass().getDeclaredField("id"), (long) 6);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
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

    @Test(expected = CategoryNotFoundException.class)
    public void findById_whenNonExistingId_shouldThrowCategoryNotFoundException(){
        CategoriesService service = new CategoriesService(categoryRepository);

        Category category = service.findById((long) 6);
    }

    @Test
    public void exists_whenExistingIdAndNotDeleted_shouldReturnTrue(){
        CategoriesService service = new CategoriesService(categoryRepository);

        Assert.assertTrue(service.exists((long) 3));
    }

    @Test
    public void exists_whenExistingIdAndDeleted_shouldReturnFalse(){
        CategoriesService service = new CategoriesService(categoryRepository);

        Assert.assertFalse(service.exists((long) 5));
    }

    @Test
    public void exists_whenNonExistingId_shouldReturnFalse(){
        CategoriesService service = new CategoriesService(categoryRepository);

        Assert.assertFalse(service.exists((long) 6));
    }

    @Test
    public void isDuplicated_withNameOnly_whenNoDuplicatedNames_shouldReturnFalse(){
        CategoriesService service = new CategoriesService(categoryRepository);

        Assert.assertFalse(service.isDuplicated("NewName"));
    }

    @Test
    public void isDuplicated_withNameOnly_whenDuplicatedNames_shouldReturnTrue(){
        CategoriesService service = new CategoriesService(categoryRepository);

        Assert.assertTrue(service.isDuplicated("Cars"));
    }

    @Test
    public void isDuplicated_withIdAndName_whenNoDuplicatedNames_shouldReturnFalse(){
        CategoriesService service = new CategoriesService(categoryRepository);

        Assert.assertFalse(service.isDuplicated((long) 9, "NewName"));
    }

    @Test
    public void isDuplicated_withIdAndName_whenDuplicatedNamesAndDifferentId_shouldReturnTrue(){
        CategoriesService service = new CategoriesService(categoryRepository);

        Assert.assertTrue(service.isDuplicated((long) 5, "NotDeletedCategory"));
    }

    @Test
    public void isDuplicated_withIdAndName_whenDuplicatedNamesAndDifferentId_butDuplicatedIsDeleted_shouldReturnFalse(){
        CategoriesService service = new CategoriesService(categoryRepository);

        Assert.assertFalse(service.isDuplicated((long) 5, "DeletedCategory"));
    }
}
