// -----------------------------------------------------
// Assignment 2
// Written by: Omar Nahhas: 40253505
// COMP249
// Due Date: 26/03/2024
// -----------------------------------------------------

package com.filmbuff.moviedb;

import com.filmbuff.moviedb.constant.Constants;
import com.filmbuff.moviedb.exception.MovieDBException;
import com.filmbuff.moviedb.util.MovieUtil;

import java.io.*;
import java.util.Scanner;

/**
 * Manages the operations related to movies.
 * This class provides methods to execute different parts of the movie
 * management process.
 */
public class MovieManager {

    private static final String BAD_MOVIE_RECORDS_FILE = "bad_movie_records.txt";
    private static final String MANIFEST_1_FILE = "part1_manifest.txt";
    private static final String MANIFEST_2_FILE = "part2_manifest.txt";
    private static final String MANIFEST_3_FILE = "part3_manifest.txt";

    /**
     * Main method to start the Movie Manager application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {

        System.out.println("Starting Movie Manager....");
        System.out.println("Deleting files from previous executions...");

        MovieUtil.deleteFileIfExists(BAD_MOVIE_RECORDS_FILE);
        MovieUtil.deleteFilesOf(MANIFEST_2_FILE);
        MovieUtil.deleteFilesOf(MANIFEST_3_FILE);

        System.out.println("Deleting files from previous executions : completed");

        // Perform Part 1
        do_part1(MANIFEST_1_FILE);

        // Perform Part 2
        do_part2(MANIFEST_2_FILE);

        // Perform Part 3
        Movie[][] all_movies = do_part3(MANIFEST_3_FILE);

        System.out.println("===========================================================");
        System.out.println("===========================================================");
        System.out.println("========================Omar Nahhas========================");
        System.out.println("===========================================================");
        System.out.println("===========================================================");

        navigateMovies(all_movies, 0);
    }

    /**
     * Performs Part 1 of the movie management process.
     *
     * @param manifestFilePath the file path of the manifest for Part 1
     */
    public static void do_part1(String manifestFilePath) {
        File manifestFile = new File(manifestFilePath);
        BufferedWriter badRecordsWriter = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(manifestFile))) {
            String movieFilePath;
            while ((movieFilePath = reader.readLine()) != null) {
                File movieFile = new File(movieFilePath);
                if (movieFile.exists()) {
                    try (BufferedReader movieReader = new BufferedReader(new FileReader(movieFile))) {
                        String line;
                        int lineNumber = 0;
                        while ((line = movieReader.readLine()) != null) {
                            lineNumber++;
                            try {
                                MovieUtil.processMovieRecord(line);
                            } catch (MovieDBException e) {
                                handleException(e, line, movieFilePath, lineNumber);
                            }
                        }
                    } catch (IOException e) {
                        System.err.println("Error reading movie file: " + e.getMessage());
                    }
                } else {
                    System.err.println("Movie file does not exist: " + movieFilePath);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading manifest file: " + e.getMessage());
        } finally {
            closeWriter(badRecordsWriter);
        }
    }

    /**
     * Handles exceptions occurred during processing of movie records.
     *
     * @param e             the MovieDBException object representing the exception
     * @param movieRecord   the movie record where the exception occurred
     * @param movieFilePath the file path of the movie where the exception occurred
     * @param lineNumber    the line number in the movie file where the exception
     *                      occurred
     */
    public static void handleException(MovieDBException e, String movieRecord, String movieFilePath, int lineNumber) {
        try {
            System.out.println(movieRecord);
            System.out.println(e.getMessage());
            BufferedWriter writer = new BufferedWriter(new FileWriter(BAD_MOVIE_RECORDS_FILE, true));
            writer.write(movieFilePath + ":" + lineNumber + ":" + e.getMessage() + ":" + movieRecord);
            writer.newLine();
            writer.close();
        } catch (IOException ioException) {
            System.err.println("Error writing bad movie record: " + ioException.getMessage());
        }
    }

    /**
     * Closes the BufferedWriter object if it's not null.
     *
     * @param writer the BufferedWriter object to close
     */
    public static void closeWriter(BufferedWriter writer) {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                System.err.println("Error closing writer: " + e.getMessage());
            }
        }
    }

    /**
     * Performs Part 2 of the movie management process.
     *
     * @param manifestFilePath the file path of the manifest for Part 2
     */
    public static void do_part2(String manifestFilePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(manifestFilePath))) {
            String genreFile;
            while ((genreFile = reader.readLine()) != null) {
                MovieUtil.processGenreFile(genreFile);
            }
        } catch (IOException e) {
            System.err.println("Error reading manifest file: " + e.getMessage());
        }
    }

    /**
     * Performs Part 3 of the movie management process.
     *
     * @param manifestFilePath the file path of the manifest for Part 3
     * @return a 2D array of Movie objects
     */
    public static Movie[][] do_part3(String manifestFilePath) {
        int totalGenres = MovieUtil.countLines(manifestFilePath);
        Movie[][] allMovies = new Movie[totalGenres][];

        int genreIndex = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(manifestFilePath))) {
            String serializedFile;
            while ((serializedFile = reader.readLine()) != null) {
                Movie[] genreMovies = MovieUtil.deserializeMovies(serializedFile);
                allMovies[genreIndex] = genreMovies;
                genreIndex += 1;
            }
        } catch (IOException e) {
            System.err.println("Error reading manifest file: " + e.getMessage());
        }

        return allMovies;

    }

    /**
     * Navigates through movies.
     *
     * @param allMovies         a 2D array of Movie objects
     * @param currentGenreIndex the index of the current genre
     */
    public static void navigateMovies(Movie[][] allMovies, int currentGenreIndex) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("-----------------------------");
            System.out.println("         Main Menu");
            System.out.println("-----------------------------");
            System.out.println("s Select a movie array to navigate");
            System.out.println("n Navigate " + Constants.VALID_GENRES.get(currentGenreIndex) + " movies " + " ("
                    + allMovies[currentGenreIndex].length + " records)");
            System.out.println("x Exit");
            System.out.print("Enter Your Choice: ");
            String choice = scanner.nextLine();

            switch (choice.toLowerCase()) {

                case "s":
                    displayGenreSubMenu(allMovies);
                    System.out.print("Enter Your Choice: ");
                    int genreChoice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline character

                    if (genreChoice >= 1 && genreChoice <= allMovies.length) {
                        currentGenreIndex = genreChoice - 1;
                        navigateMovies(allMovies, currentGenreIndex);
                        return;
                    } else {
                        System.out.println("Invalid genre choice. Please try again.");
                    }
                    break;
                case "n":
                    displayMovies(allMovies[currentGenreIndex], Constants.VALID_GENRES.get(currentGenreIndex), 0);
                    return;
                case "x":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Displays the genre submenu.
     *
     * @param allMovies A two-dimensional array containing all movies.
     */
    public static void displayGenreSubMenu(Movie[][] allMovies) {
        System.out.println("------------------------------");
        System.out.println("       Genre Sub-Menu");
        System.out.println("------------------------------");
        for (int i = 0; i < allMovies.length; i++) {
            System.out.println((i + 1) + " " + Constants.VALID_GENRES.get(i) + " (" + allMovies[i].length + " movies)");
        }
        System.out.println((allMovies.length + 1) + " Exit");
    }

    /**
     * Displays movies for a specific genre.
     *
     * @param movies     An array of movies for a specific genre.
     * @param genre      The genre of the movies.
     * @param startIndex The start index for displaying movies.
     */

    public static void displayMovies(Movie[] movies, String genre, int startIndex) {
        Scanner scanner = new Scanner(System.in);
        int currentIndex = startIndex;

        while (true) {
            System.out.println("Navigating " + genre + " movies " + "(" + movies.length + ")");
            System.out.println("Enter Your Choice:");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            if (choice == 0) {
                System.out.println("End of session.");
                return;
            } else if (choice < 0) {
                int absChoice = Math.abs(choice);
                int newIndex = currentIndex - absChoice + 1;
                if (newIndex < 0) {
                    System.out.println("BOF has been reached.");
                    newIndex = 0;
                }
                displayRangeOfMovies(movies, newIndex, currentIndex);
                currentIndex = newIndex;
            } else {
                int newIndex = currentIndex + choice - 1;
                if (newIndex >= movies.length) {
                    System.out.println("EOF has been reached.");
                    newIndex = movies.length - 1;
                }
                displayRangeOfMovies(movies, currentIndex, newIndex);
                currentIndex = newIndex;
            }
        }
    }

    /**
     * Displays a range of movies based on the start and end indexes.
     *
     * @param movies     An array of movies.
     * @param startIndex The start index.
     * @param endIndex   The end index.
     */
    public static void displayRangeOfMovies(Movie[] movies, int startIndex, int endIndex) {
        for (int i = startIndex; i <= endIndex; i++) {

            System.out.println("[" + (i) + "] : " + movies[i]); // Display other records

        }
    }
}
