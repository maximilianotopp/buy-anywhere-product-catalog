package com.buyanywhere.productcatalog.configuration;

import com.buyanywhere.productcatalog.dto.CategoryDto;
import com.buyanywhere.productcatalog.models.Category;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfiguration {

    private static ModelMapper mapper;

    @Bean
    public ModelMapper modelMapper(){
        if(mapper != null){
            return mapper;
        }

        mapper = new ModelMapper();
        mapper.createTypeMap(Category.class, CategoryDto.class);

        return mapper;
    }
}
