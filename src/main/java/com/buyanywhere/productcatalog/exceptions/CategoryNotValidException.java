package com.buyanywhere.productcatalog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class CategoryNotValidException extends RuntimeException{
    public CategoryNotValidException(String invalidAttribute){
        super("The Category has the following invalid value(s): " + invalidAttribute);
    }
}
