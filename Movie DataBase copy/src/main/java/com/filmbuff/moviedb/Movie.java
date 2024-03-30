// -----------------------------------------------------
// Assignment 2
// Written by: Omar Nahhas: 40253505
// COMP249
// Due Date: 26/03/2024
// -----------------------------------------------------
package com.filmbuff.moviedb;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a movie.
 * This class implements Serializable to allow for object serialization.
 */
public class Movie implements Serializable {

    // Instance variables
    private int year;
    private String title;
    private int duration;
    private String genre;
    private String rating;
    private double score;
    private String director;
    private String actor1;
    private String actor2;
    private String actor3;

    /**
     * Constructs a Movie object with the specified attributes.
     *
     * @param year     the year of the movie
     * @param title    the title of the movie
     * @param duration the duration of the movie
     * @param genre    the genre of the movie
     * @param rating   the rating of the movie
     * @param score    the score of the movie
     * @param director the director of the movie
     * @param actor1   the name of the first actor
     * @param actor2   the name of the second actor
     * @param actor3   the name of the third actor
     */
    public Movie(int year, String title, int duration, String genre, String rating,
            double score, String director, String actor1, String actor2, String actor3) {
        this.year = year;
        this.title = title;
        this.duration = duration;
        this.genre = genre;
        this.rating = rating;
        this.score = score;
        this.director = director;
        this.actor1 = actor1;
        this.actor2 = actor2;
        this.actor3 = actor3;
    }

    /**
     * Compares this Movie object with another object for equality.
     *
     * @param o the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Movie movie = (Movie) o;
        return year == movie.year &&
                duration == movie.duration &&
                Double.compare(movie.score, score) == 0 &&
                title.equals(movie.title) &&
                genre.equals(movie.genre) &&
                rating.equals(movie.rating) &&
                director.equals(movie.director) &&
                actor1.equals(movie.actor1) &&
                actor2.equals(movie.actor2) &&
                actor3.equals(movie.actor3);
    }

    /**
     * Generates a hash code value for the Movie object.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(year, title, genre, duration, rating, score, director, actor1, actor2, actor3);
        result = 31 * result;
        return result;
    }

    /**
     * Returns a string representation of the Movie object.
     *
     * @return a string representation
     */
    @Override
    public String toString() {
        return "{" +
                "year=" + year +
                ", title='" + title + '\'' +
                ", duration=" + duration +
                ", genre='" + genre + '\'' +
                ", rating='" + rating + '\'' +
                ", score=" + score +
                ", director='" + director + '\'' +
                ", actor1='" + actor1 + '\'' +
                ", actor2='" + actor2 + '\'' +
                ", actor3='" + actor3 + '\'' +
                '}';
    }
}
