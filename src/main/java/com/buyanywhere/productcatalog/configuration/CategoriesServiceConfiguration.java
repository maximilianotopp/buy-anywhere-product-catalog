package com.buyanywhere.productcatalog.configuration;

import com.buyanywhere.productcatalog.services.CategoriesService;
import com.buyanywhere.productcatalog.services.ICategoriesService;
import com.buyanywhere.productcatalog.repositories.ICategoryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoriesServiceConfiguration {
    @Bean
    public ICategoriesService categoriesService(ICategoryRepository repository){
        return new CategoriesService(repository);
    }
}
