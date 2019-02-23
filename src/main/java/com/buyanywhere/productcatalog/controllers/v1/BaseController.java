package com.buyanywhere.productcatalog.controllers.v1;

import org.modelmapper.ModelMapper;

public abstract class BaseController {
    protected ModelMapper mapper;

    protected BaseController(ModelMapper mapper) {
        this.mapper = mapper;
    }
}
