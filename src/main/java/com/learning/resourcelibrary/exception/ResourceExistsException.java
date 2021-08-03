package com.learning.resourcelibrary.exception;

public class ResourceExistsException extends RuntimeException{
    public ResourceExistsException(String message) {
        super(message);
    }
}
