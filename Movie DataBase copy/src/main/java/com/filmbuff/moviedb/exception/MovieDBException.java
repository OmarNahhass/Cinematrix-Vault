// -----------------------------------------------------
// Assignment 2
// Written by: Omar Nahhas: 40253505
// COMP249
// Due Date: 26/03/2024
// -----------------------------------------------------
package com.filmbuff.moviedb.exception;

/**
 * An abstract class representing exceptions specific to the MovieDB
 * application.
 * This class extends the Exception class, making it a checked exception.
 */
public abstract class MovieDBException extends Exception {

    /**
     * Constructs a new MovieDBException with the specified detail message and movie
     * record.
     *
     * @param msg         The detail message.
     * @param movieRecord The movie record associated with the exception.
     */
    protected MovieDBException(String msg, String movieRecord) {
        super(msg); // Call superclass constructor with message
        System.err.println(msg + "[" + movieRecord + "]"); // Print error message with movie record
    }
}
