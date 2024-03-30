// -----------------------------------------------------
// Assignment 2
// Written by: Omar Nahhas: 40253505
// COMP249
// Due Date: 26/03/2024
// -----------------------------------------------------
package com.filmbuff.moviedb.util;

import com.filmbuff.moviedb.Movie;
import com.filmbuff.moviedb.exception.ExcessFieldsException;
import com.filmbuff.moviedb.exception.MissingFieldsException;
import com.filmbuff.moviedb.exception.MissingQuotesException;
import com.filmbuff.moviedb.exception.MovieDBException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for performing operations related to movies.
 */
public class MovieUtil {

    private MovieUtil() {
    }

    /**
     * Deletes the file if it exists.
     *
     * @param filePath the path of the file to delete
     */
    public static void deleteFileIfExists(String filePath) {
        // Convert the file path to a Path object
        Path path = Paths.get(filePath);

        // Check if the file exists
        if (Files.exists(path)) {
            // Delete the file
            try {
                Files.delete(path);
                System.out.println(filePath + " : deleted");
            } catch (Exception e) {
                System.err.println("Error deleting the file: " + e.getMessage());
            }
        }

    }

    /**
     * Deletes files specified in the manifest file.
     *
     * @param manifestFilePath the path of the manifest file
     */
    public static void deleteFilesOf(String manifestFilePath) {
        File manifestFile = new File(manifestFilePath);

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(manifestFile));
            String movieFilePath;
            while ((movieFilePath = reader.readLine()) != null) {
                deleteFileIfExists(movieFilePath);
            }
        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.err.println("Error occurred: " + e.getMessage());
            }
        }

    }

    /**
     * Counts the occurrences of a character in a string.
     *
     * @param str       the string to search
     * @param character the character to count
     * @return the number of occurrences of the character
     */
    private static int countOccurrencesOfCharacter(String str, char character) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == character) {
                count++;
            }
        }
        return count;
    }

    /**
     * Gets the fields from a movie record.
     *
     * @param movieRecord the movie record string
     * @return an array containing the fields of the movie record
     * @throws MovieDBException if there is an error processing the movie record
     */
    private static String[] getFields(String movieRecord) throws MovieDBException {
        if (countOccurrencesOfCharacter(movieRecord, '\"') % 2 == 1) {
            throw new MissingQuotesException("Missing quotes", movieRecord);
        }
        String[] fields = new String[10];
        StringBuilder fieldBuilder = new StringBuilder();
        int index = 0;
        boolean withinQuotes = false;

        for (char c : movieRecord.toCharArray()) {
            if (c == '"') {
                withinQuotes = !withinQuotes; // Toggle the flag
            } else if (c == ',' && !withinQuotes) {
                fields[index++] = fieldBuilder.toString();
                fieldBuilder.setLength(0); // Clear the StringBuilder
            } else {
                fieldBuilder.append(c);
            }
        }

        if (index > 9) {
            throw new ExcessFieldsException("Excess fields", movieRecord);
        }

        // Add the last field
        fields[index] = fieldBuilder.toString();

        if (index != 9) {
            throw new ExcessFieldsException("Excess fields", movieRecord);
        }

        return fields;
    }

    /**
     * Writes the movie record to a genre-specific CSV file.
     *
     * @param movieRecord the movie record to write
     * @param genre       the genre of the movie
     */
    public static void writeMovieRecord(String movieRecord, String genre) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(genre + ".csv", true));
            writer.write(movieRecord);
            writer.newLine();
            writer.close();

        } catch (IOException ioException) {
            System.err.println("Error writing movie record: " + ioException.getMessage());
        }
    }

    /**
     * Processes a movie record, validates its fields, and writes it to the
     * corresponding genre file.
     *
     * @param movieRecord the movie record to process
     * @throws MovieDBException if there is an error processing the movie record
     */
    public static void processMovieRecord(String movieRecord) throws MovieDBException {

        String[] fields = getFields(movieRecord);

        // Extract fields from the record
        int year;
        String title;
        String genre;
        String rating;
        String director;
        String actor1;
        String actor2;
        String actor3;
        int duration;
        double score;

        try {
            year = Integer.parseInt(fields[0]);
            title = fields[1].trim();
            duration = Integer.parseInt(fields[2]);
            genre = fields[3].trim().toLowerCase();
            rating = fields[4].trim();
            score = Double.parseDouble(fields[5]);
            director = fields[6].trim();
            actor1 = fields[7].trim();
            actor2 = fields[8].trim();
            actor3 = fields[9].trim();

            ValidationUtil.validateFields(year, title, duration, genre, rating, score, actor1, actor2, actor3,
                    movieRecord);

            writeMovieRecord(movieRecord, genre);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new MissingFieldsException("Missing fields", movieRecord);
        }
    }

    /**
     * Processes a genre file, parses its records, and serializes the movies.
     *
     * @param genreFile the path of the genre file to process
     */
    public static void processGenreFile(String genreFile) {
        List<Movie> movies = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(genreFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Movie movie = parseMovieRecord(line);
                if (movie != null) {
                    movies.add(movie);
                }
            }
        } catch (IOException e) {
            // do nothing
        }

        serializeMovies(movies, genreFile.split(".csv")[0] + ".ser");
    }

    /**
     * Parses a movie record string and creates a Movie object.
     *
     * @param record the movie record string to parse
     * @return the Movie object parsed from the record, or null if parsing fails
     */
    private static Movie parseMovieRecord(String record) {
        String[] fields = record.split(",");
        if (fields.length != 10) {
            // Log error or handle malformed record
            return null;
        }

        try {
            int year = Integer.parseInt(fields[0]);
            String title = fields[1].trim();
            int duration = Integer.parseInt(fields[2]);
            String genre = fields[3].trim().toLowerCase();
            String rating = fields[4].trim();
            double score = Double.parseDouble(fields[5]);
            String director = fields[6].trim();
            String actor1 = fields[7].trim();
            String actor2 = fields[8].trim();
            String actor3 = fields[9].trim();

            // Create and return a Movie object
            return new Movie(year, title, duration, genre, rating, score, director, actor1, actor2, actor3);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            // Log error or handle invalid fields
            return null;
        }
    }

    /**
     * Serializes a list of movies to a file.
     *
     * @param movies     the list of movies to serialize
     * @param outputFile the path of the output file
     */
    private static void serializeMovies(List<Movie> movies, String outputFile) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(outputFile))) {
            oos.writeObject(movies.toArray(new Movie[0]));
        } catch (IOException e) {
            System.err.println("Error serializing movies: " + e.getMessage());
        }
    }

    /**
     * Counts the number of lines in a file.
     *
     * @param filePath the path of the file to count lines
     * @return the number of lines in the file
     */
    public static int countLines(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            int lines = 0;
            while (reader.readLine() != null) {
                lines++;
            }
            return lines;
        } catch (IOException e) {
            System.err.println("Error counting lines: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Deserializes movies from a serialized file.
     *
     * @param serializedFile the path of the serialized file
     * @return an array of deserialized Movie objects
     */
    public static Movie[] deserializeMovies(String serializedFile) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(serializedFile))) {
            Object obj = ois.readObject();
            if (obj instanceof Movie[]) {
                return (Movie[]) obj;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error deserializing movies: " + e.getMessage());
        }
        return new Movie[0];
    }
}
