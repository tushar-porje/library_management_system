package com.kane.library_management_system.library_management_system.exception;

public class BookUnavailableException extends RuntimeException{
    public BookUnavailableException(long bookId){
        super(String.format("book with id: %d is unavailable at a moment", bookId));
    }
}
