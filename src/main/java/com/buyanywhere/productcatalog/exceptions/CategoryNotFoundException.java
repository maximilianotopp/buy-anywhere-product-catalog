package com.buyanywhere.productcatalog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        value= HttpStatus.NOT_FOUND
)
public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(long id){
        super("Could not find Category with id " + id);
    }
}
