package com.buyanywhere.productcatalog.repositories;

import com.buyanywhere.productcatalog.models.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository <Category, Long> {
}