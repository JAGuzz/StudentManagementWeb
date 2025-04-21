package com.web.studentsApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceName, String fieldName, String fielValue){
        super(String.format("%s not foud with the given input data %s: '%s'", resourceName, fieldName, fielValue));
    }

}
