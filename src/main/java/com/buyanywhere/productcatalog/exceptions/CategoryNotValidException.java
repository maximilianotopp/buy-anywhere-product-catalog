package com.buyanywhere.productcatalog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class CategoryNotValidException extends RuntimeException{
    public CategoryNotValidException(){
        super("The arguments passed are not valid.");
    }
}
