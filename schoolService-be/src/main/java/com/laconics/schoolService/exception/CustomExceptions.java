package com.laconics.schoolService.exception;

public class CustomExceptions {

    public static class UserExistsException extends RuntimeException {
        public UserExistsException(String message) {
            super(message);
        }
    }

}
