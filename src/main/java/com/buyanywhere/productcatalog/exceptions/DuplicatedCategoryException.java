package com.buyanywhere.productcatalog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class DuplicatedCategoryException extends RuntimeException {
    public DuplicatedCategoryException(String name){
        super("The Category " + name + " is duplicated.");
    }
}
