package com.buyanywhere.productcatalog.services;

import com.buyanywhere.productcatalog.exceptions.CategoryNotFoundException;
import com.buyanywhere.productcatalog.models.Category;
import com.buyanywhere.productcatalog.repositories.ICategoryRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.FieldSetter;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

@RunWith(SpringRunner.class)
public class CategoriesServiceTest {

    @MockBean
    private ICategoryRepository categoryRepository;

    @Test
    public void findById_whenExistingIdAndNotDeleted_shouldReturnCategory(){
        final long id = 3;
        final String name = "Electronics";
        final int displayOrder = 7;

        CategoriesService service = new CategoriesService(categoryRepository);

        Category category = new Category(name, displayOrder);
        SetField(category, "id", id);

        MockFindById(id, category);

        Category categoryResult = service.findById(id);

        Assert.assertEquals(name, categoryResult.getName());
        Assert.assertEquals(displayOrder, categoryResult.getDisplayOrder());
    }

    @Test(expected = CategoryNotFoundException.class)
    public void findById_whenExistingIdAndDeleted_shouldThrowCategoryNotFoundException(){
        final long id = 5;

        CategoriesService service = new CategoriesService(categoryRepository);

        Category deletedCategory = new Category("DeletedCategory", 5);
        deletedCategory.delete();
        SetField(deletedCategory, "id", id);

        MockFindById(id, deletedCategory);

        service.findById(id);
    }

    @Test(expected = CategoryNotFoundException.class)
    public void findById_whenNonExistingId_shouldThrowCategoryNotFoundException(){
        CategoriesService service = new CategoriesService(categoryRepository);

        service.findById((long) 6);
    }

    @Test
    public void exists_whenExistingIdAndNotDeleted_shouldReturnTrue(){
        final long id = 3;

        CategoriesService service = new CategoriesService(categoryRepository);

        Category category = new Category("Electronics", 1);

        MockFindById(id, category);

        Assert.assertTrue(service.exists(id));
    }

    @Test
    public void exists_whenExistingIdAndDeleted_shouldReturnFalse(){
        final long id = 5;

        CategoriesService service = new CategoriesService(categoryRepository);

        Category deletedCategory = new Category("Computers", 4);
        deletedCategory.delete();

        MockFindById(id, deletedCategory);

        Assert.assertFalse(service.exists(id));
    }

    @Test
    public void exists_whenNonExistingId_shouldReturnFalse(){
        CategoriesService service = new CategoriesService(categoryRepository);

        Assert.assertFalse(service.exists((long) 6));
    }

    @Test
    public void isDuplicated_withNameOnly_whenNoDuplicatedNames_shouldReturnFalse(){
        final String name = "NewName";

        CategoriesService service = new CategoriesService(categoryRepository);

        MockFindByNameAndDeletedFalse(name, new ArrayList<>());

        Assert.assertFalse(service.isDuplicated(name));
    }

    @Test
    public void isDuplicated_withNameOnly_whenDuplicatedNames_shouldReturnTrue(){
        final String name = "Cars";

        CategoriesService service = new CategoriesService(categoryRepository);

        Category category = new Category(name, 2);

        MockFindByNameAndDeletedFalse(name, new ArrayList<>(Collections.singleton(category)));

        Assert.assertTrue(service.isDuplicated(name));
    }

    @Test
    public void isDuplicated_withIdAndName_whenNoDuplicatedNames_shouldReturnFalse(){
        final String name = "NewName";

        CategoriesService service = new CategoriesService(categoryRepository);

        MockFindByNameAndDeletedFalse(name, new ArrayList<>());

        Assert.assertFalse(service.isDuplicated((long) 9, name));
    }

    @Test
    public void isDuplicated_withIdAndName_whenDuplicatedNamesAndDifferentId_shouldReturnTrue(){
        final String name = "NotDeletedCategory";

        CategoriesService service = new CategoriesService(categoryRepository);

        Category notDeletedCategory = new Category(name, 4);
        SetField(notDeletedCategory, "id", (long) 6);

        MockFindByNameAndDeletedFalse(name, new ArrayList<>(Collections.singleton(notDeletedCategory)));

        Assert.assertTrue(service.isDuplicated((long) 5, name));
    }

    @Test
    public void isDuplicated_withIdAndName_whenDuplicatedNamesAndDifferentId_butDuplicatedIsDeleted_shouldReturnFalse(){
        final String name = "DeletedCategory";

        CategoriesService service = new CategoriesService(categoryRepository);

        MockFindByNameAndDeletedFalse(name, new ArrayList<>());

        Assert.assertFalse(service.isDuplicated((long) 5, name));
    }

    private void MockFindById(long id, Category returnCategory){
        Mockito.when(categoryRepository.findById(id)).thenReturn(Optional.ofNullable(returnCategory));
    }

    private void MockFindByNameAndDeletedFalse(String name, ArrayList<Category> categories){
        Mockito.when(categoryRepository.findByNameAndDeletedFalse(name)).thenReturn(categories);
    }

    private void SetField(Category category, String fieldName, Object value){
        try {
            FieldSetter.setField(category, category.getClass().getDeclaredField(fieldName), value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
