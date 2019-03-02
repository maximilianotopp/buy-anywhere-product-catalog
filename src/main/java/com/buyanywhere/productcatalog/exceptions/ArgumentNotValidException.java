package com.buyanywhere.productcatalog.exceptions;

import com.buyanywhere.productcatalog.models.Category;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class ArgumentNotValidException extends RuntimeException{

    public ArgumentNotValidException(String argument) {
        super("Argument not valid: " + argument);
    }
}
