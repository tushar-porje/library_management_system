package com.kane.library_management_system.library_management_system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> resourseNotFoundException(ResourceNotFoundException rs){
        String message=rs.getMessage();
        return new ResponseEntity<String>(message,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookUnavailableException.class)
    public ResponseEntity<String> bookUnavailableException(BookUnavailableException rs){
        String message=rs.getMessage();
        return new ResponseEntity<String>(message,HttpStatus.BAD_REQUEST);
    }


}
