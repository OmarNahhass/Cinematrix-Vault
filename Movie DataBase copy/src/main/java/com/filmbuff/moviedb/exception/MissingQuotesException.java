// -----------------------------------------------------
// Assignment 2
// Written by: Omar Nahhas: 40253505
// COMP249
// Due Date: 26/03/2024
// -----------------------------------------------------
package com.filmbuff.moviedb.exception;

/**
 * Exception thrown when quotes are missing in a movie record.
 */
public class MissingQuotesException extends MovieDBException {

    /**
     * Constructs a new MissingQuotesException with the specified detail message and
     * movie record.
     *
     * @param msg         the detail message
     * @param movieRecord the movie record where the error occurred
     */
    public MissingQuotesException(String msg, String movieRecord) {
        super(msg, movieRecord);
    }
}
