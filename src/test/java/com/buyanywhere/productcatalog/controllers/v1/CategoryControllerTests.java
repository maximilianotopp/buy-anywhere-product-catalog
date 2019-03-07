package com.buyanywhere.productcatalog.controllers.v1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CategoryControllerTests {
    @Test
    public void GetWithExistingIdShouldReturnCategory(){
    }
}
