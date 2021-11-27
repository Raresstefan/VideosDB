package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * General information about show (video), retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public abstract class Video {
    /**
     * Show's title
     */
    private final String title;
    /**
     * The year the show was released
     */
    private final int year;
    /**
     * Show casting
     */
    private final ArrayList<String> cast;
    /**
     * Show genres
     */
    private final ArrayList<String> genres;
    public Video(final String title, final int year,
                     final ArrayList<String> cast, final ArrayList<String> genres) {
        this.title = title;
        this.year = year;
        this.cast = cast;
        this.genres = genres;
    }
    /**
     * getter for the title of the video
     */
    public final String getTitle() {
        return title;
    }
    /**
     * getter for the year in which the video appeared
     */
    public final int getYear() {
        return year;
    }
    /**
     * getter for the cast of the video
     */
    public final ArrayList<String> getCast() {
        return cast;
    }
    /**
     * getter for the genres of the video
     */
    public final ArrayList<String> getGenres() {
        return genres;
    }
}
