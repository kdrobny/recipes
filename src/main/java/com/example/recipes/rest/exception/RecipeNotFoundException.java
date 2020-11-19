package com.example.recipes.rest.exception;

public class RecipeNotFoundException extends RuntimeException {

    public RecipeNotFoundException() {
        super();
    }

    public RecipeNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public RecipeNotFoundException(final String message) {
        super(message);
    }

    public RecipeNotFoundException(final Throwable cause) {
        super(cause);
    }

}
