// -----------------------------------------------------
// Assignment 2
// Written by: Omar Nahhas: 40253505
// COMP249
// Due Date: 26/03/2024
// -----------------------------------------------------
package com.filmbuff.moviedb.exception;

/**
 * Exception thrown when a movie has missing or invalid name.
 */
public class BadNameException extends MovieDBException {

    /**
     * Constructs a new BadNameException with the specified detail message and movie
     * record.
     *
     * @param msg         the detail message
     * @param movieRecord the movie record where the error occurred
     */
    public BadNameException(String msg, String movieRecord) {
        super(msg, movieRecord);
    }
}
