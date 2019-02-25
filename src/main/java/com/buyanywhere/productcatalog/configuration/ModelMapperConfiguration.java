package com.buyanywhere.productcatalog.configuration;

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

        return mapper;
    }
}
