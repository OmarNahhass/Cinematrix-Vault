// -----------------------------------------------------
// Assignment 2
// Written by: Omar Nahhas: 40253505
// COMP249
// Due Date: 26/03/2024
// -----------------------------------------------------
package com.filmbuff.moviedb.util;

import com.filmbuff.moviedb.constant.Constants;
import com.filmbuff.moviedb.exception.*;

/**
 * Utility class for validating movie fields.
 */
public class ValidationUtil {

    private ValidationUtil() {
    }

    /**
     * Validates the fields of a movie record.
     *
     * @param year        the year of the movie
     * @param title       the title of the movie
     * @param duration    the duration of the movie
     * @param genre       the genre of the movie
     * @param rating      the rating of the movie
     * @param score       the score of the movie
     * @param actor1      the first actor of the movie
     * @param actor2      the second actor of the movie
     * @param actor3      the third actor of the movie
     * @param movieRecord the movie record string
     * @throws MovieDBException if any of the fields are invalid
     */
    public static void validateFields(int year, String title, int duration, String genre, String rating,
            double score, String actor1, String actor2, String actor3, String movieRecord) throws MovieDBException {
        if (year < 1990 || year > 1999) {
            throw new BadYearException("Invalid year", movieRecord);
        } else if (duration < 30 || duration > 300) {
            throw new BadDurationException("Invalid duration", movieRecord);
        } else if (Constants.VALID_RATINGS.notContains(rating.toLowerCase())) {
            throw new BadRatingException("Invalid rating", movieRecord);
        } else if (score < 0 || score > 10) {
            throw new BadScoreException("Invalid score", movieRecord);
        } else if (title.isEmpty()) {
            throw new BadTitleException("Missing title", movieRecord);
        } else if (actor1.isEmpty() || actor2.isEmpty() || actor3.isEmpty()) {
            throw new BadNameException("Missing name(s)", movieRecord);
        } else if (Constants.VALID_GENRES.notContains(genre)) {
            throw new BadGenreException("Invalid genre", movieRecord);
        }
    }
}
