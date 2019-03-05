package com.buyanywhere.productcatalog.repositories;

import com.buyanywhere.productcatalog.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
