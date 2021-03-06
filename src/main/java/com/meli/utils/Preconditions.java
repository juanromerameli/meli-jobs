package com.meli.utils;

import java.util.Collection;

/**
 *
 *
 */
public final class Preconditions {

    private static final String DEFAULT_MESSAGE = "Received an invalid parameter";

    private Preconditions() {
    }


    /**
     * Checks that an object is not null.
     *
     * @param object   any object
     * @param errorMsg error message
     * @throws IllegalArgumentException if the object is null
     */
    public static void checkNotNull(Object object, String errorMsg) {
        check(object != null, errorMsg);
    }

    /**
     * Checks that a string is not null or empty
     *
     * @param string   any string
     * @param errorMsg error message
     * @throws IllegalArgumentException if the string is null or empty
     */
    public static void checkNullOrEmpty(String string, String errorMsg) {
        check(string != null && !string.trim().equals(""), errorMsg);
    }

    /**
     * Checks that a collection is not null or empty
     *
     * @param collection any collection
     * @param errorMsg   error message
     * @throws IllegalArgumentException if the collection is null or empty
     */
    public static void checkNullOrEmpty(Collection collection, String errorMsg) {
        check(!collection.isEmpty(), errorMsg);
    }

    private static void check(boolean requirements, String error) {
        String message = (error == null || error.trim().length() <= 0) ? DEFAULT_MESSAGE : error;
        if (!requirements) {
            throw new IllegalArgumentException(message);
        }
    }


}
