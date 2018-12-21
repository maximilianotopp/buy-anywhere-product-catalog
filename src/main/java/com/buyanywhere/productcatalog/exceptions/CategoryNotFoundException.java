package com.buyanywhere.productcatalog.exceptions;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(long id){
        super("Could not find Category with id " + id);
    }
}
