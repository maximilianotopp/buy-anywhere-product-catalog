package com.buyanywhere.productcatalog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        value= HttpStatus.NOT_FOUND,
        reason="No such Category found"
)
public class CategoryNotFoundException extends RuntimeException {
}
