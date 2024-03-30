// -----------------------------------------------------
// Assignment 2
// Written by: Omar Nahhas: 40253505
// COMP249
// Due Date: 26/03/2024
// -----------------------------------------------------
package com.filmbuff.moviedb.exception;

/**
 * Exception thrown when a movie has an invalid year.
 */
public class BadYearException extends MovieDBException {

    /**
     * Constructs a new BadYearException with the specified detail message and movie
     * record.
     *
     * @param msg         the detail message
     * @param movieRecord the movie record where the error occurred
     */
    public BadYearException(String msg, String movieRecord) {
        super(msg, movieRecord);
    }
}
