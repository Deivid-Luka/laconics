package com.laconics.schoolService.exception;

public class CustomExceptions {

    public static class ItemExistsException extends RuntimeException {
        public ItemExistsException(String message) {
            super(message);
        }

    }

    public static class ItemNotFoundException extends RuntimeException {
        public ItemNotFoundException(String message) {
            super(message);
        }

    }

    public static class ItemSavingFailedException extends RuntimeException {
        public ItemSavingFailedException(String message) {
            super(message);
        }

    }

}
