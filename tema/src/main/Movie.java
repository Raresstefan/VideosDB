package main;

import java.util.ArrayList;


/**
 * Information about a movie, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public final class Movie extends Video {
    /**
     * Duration in minutes of a season
     */
    private final int duration;

    public Movie(final String title, final ArrayList<String> cast,
                          final ArrayList<String> genres, final int year,
                          final int duration) {
        super(title, year, cast, genres);
        this.duration = duration;
    }
    /**
     * getter for the duration of the movie
     */
    public int getDuration() {
        return duration;
    }
    /**
     * toString method for this class
     */
    @Override
    public String toString() {
        return "MovieInputData{" + "title= "
                + super.getTitle() + "year= "
                + super.getYear() + "duration= "
                + duration + "cast {"
                + super.getCast() + " }\n"
                + "genres {" + super.getGenres() + " }\n ";
    }
}
