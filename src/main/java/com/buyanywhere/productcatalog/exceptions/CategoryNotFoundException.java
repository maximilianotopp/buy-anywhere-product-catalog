package com.buyanywhere.productcatalog.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value= HttpStatus.NOT_FOUND)  // 404
public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException (int id){
        super("No category found with Id: "+ id);
    }
}