package com.kane.library_management_system.library_management_system.exception;

public class ResourceNotFoundException extends RuntimeException {

    String resourceName;
    String fieldName;
    long fieldValue;

    public ResourceNotFoundException( String resourceName, String fieldName, long fieldValue) {
        super(String.format("%s not found with %s : %s", resourceName,fieldName,fieldValue));
    }

    public ResourceNotFoundException( String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s not found with %s = %s", resourceName,fieldName,fieldValue));
    }

    public ResourceNotFoundException(String resourceName, Long userId, Long bookId) {
        super(String.format("%s not found with userid: %d and bookId: %d \n"+
                                    "user with id:"+userId+" has not issued book with id:"+bookId +"", resourceName,userId,bookId));
    }

    public ResourceNotFoundException(String resouceName) {
        super(String.format("no %s found", resouceName));
    }
    
}
