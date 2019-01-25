package com.buyanywhere.productcatalog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        value= HttpStatus.BAD_REQUEST
)
public class ArgumentNotValidException extends RuntimeException{

    public ArgumentNotValidException(String argument){
        super("The argument " + argument + " is invalid.");
    }
}
