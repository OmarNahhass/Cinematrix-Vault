// -----------------------------------------------------
// Assignment 2
// Written by: Omar Nahhas: 40253505
// COMP249
// Due Date: 26/03/2024
// -----------------------------------------------------
package com.filmbuff.moviedb.constant;

import com.filmbuff.moviedb.helper.CustomListImpl;

/**
 * A class containing constant values used in the MovieDB application.
 */
public class Constants {

        // Private constructor to prevent instantiation of Constants class
        private Constants() {

        }

        /**
         * A CustomListImpl containing valid genres for movies.
         * This list is used for validation purposes.
         */
        public static final CustomListImpl VALID_GENRES = new CustomListImpl(
                        "musical", "comedy", "animation", "adventure", "drama", "crime",
                        "biography", "horror", "action", "documentary", "fantasy", "mystery",
                        "sci-fi", "family", "romance", "thriller", "western");

        /**
         * A CustomListImpl containing valid ratings for movies.
         * This list is used for validation purposes.
         */
        public static final CustomListImpl VALID_RATINGS = new CustomListImpl(
                        "pg", "unrated", "g", "r", "pg-13", "nc-17");

}
