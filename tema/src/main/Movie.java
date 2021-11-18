package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    /**
     * List of ratings given by users
     */
    private ArrayList<Double> ratings;
    /**
     * Average rating based on the list of ratings
     */
    private Double averageRating;
    private Map<String, Double> userWithRatings;
    private int views;

    public Movie(final String title, final ArrayList<String> cast,
                          final ArrayList<String> genres, final int year,
                          final int duration) {
        super(title, year, cast, genres);
        this.duration = duration;
        this.averageRating = 0.0;
        this.ratings = new ArrayList<>();
        this.views = 0;
        this.userWithRatings = new HashMap<String, Double>();
    }
    /**
     * getter for the duration of the movie
     */
    public int getDuration() {
        return duration;
    }
    /**
     * getter for the ratings of the movie
     */
    public ArrayList<Double> getRatings() {
        return ratings;
    }
    /**
     * getter for the average rating of the movie
     */
    public Double getAverageRating() {
        return averageRating;
    }
    public void calculateAverageRating() {
        Double sum = 0.0;
        for (Double rating : this.ratings) {
            sum += rating;
        }
        this.averageRating = sum / (this.ratings.size());
    }
    public void incrementViews(final int increment) {
        this.views += increment;
    }
    public String addRating(final User user, final Double grade) {
        String message = new String();
        // se verifica daca userul a vazut deja filmul -> apeleaza verifyIfIsSeen
        if(!(user.verifyIfIsSeen(this.getTitle()))) {
            // eroare -> nu a fost vazut
            String error = new String("error -> ");
            message = message + error;
            message = message + this.getTitle();
            String endString = new String(" is not seen");
            message = message + endString;
            return message;
        }
        if(userWithRatings.containsKey(user.getUsername())) {
            // eroare -> user a dat deja rating la acest film
            String error = new String("error -> ");
            message = message + error;
            message = message + this.getTitle();
            String endString = new String(" has been already rated");
            message = message + endString;
            return message;
        }
        // in acest caz se poate da rate -> succes
        this.userWithRatings.put(user.getUsername(), grade);
        String succes = new String("success -> ");
        succes = succes + this.getTitle();
        String middleString = new String(" was rated with ");
        String gradeString = grade.toString();
        middleString = middleString + gradeString;
        succes = succes + middleString;
        String str = new String(" by ");
        succes = succes + str;
        succes = succes + user.getUsername();
        message = message + succes;
        this.ratings.add(grade);
        this.calculateAverageRating();
        return message;
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
