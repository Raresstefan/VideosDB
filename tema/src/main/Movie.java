package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    /**
     * map where the key is the username of a user and the value is
     * the rated given from the user to the movie
     */
    private Map<String, Double> userWithRatings;
    /**
     * Number of the views of this movie
     */
    private int views;
    /**
     * Number of the occurences in favorite lists of this movie
     */
    private int favoriteOccurences;
    /**
     * List of users's usernames that has seen this movie
     */
    private List<String> usersThatSeen;
    /**
     * Total number of views of this movie
     */
    private int totalViews;
    /**
     * Represents a status -> tells if we can apply incrementViews method or not
     * If this method has been already applied for a show than the status is false
     * and can't be applied anymore
     */
    private boolean goodToIncrement;
    public Movie(final String title, final ArrayList<String> cast,
                 final ArrayList<String> genres, final int year,
                 final int duration) {
        super(title, year, cast, genres);
        this.duration = duration;
        this.averageRating = 0.0;
        this.ratings = new ArrayList<>();
        this.views = 0;
        this.userWithRatings = new HashMap<String, Double>();
        this.favoriteOccurences = 0;
        this.usersThatSeen = new ArrayList<>();
        this.totalViews = 0;
        this.goodToIncrement = true;
    }
    /**
     * Method that sets the status with false value
     */
    public void setFalse() {
        this.goodToIncrement = false;
    }
    /**
     * Getter for goodToIncrement status
     */
    public boolean getGoodToIncrement() {
        return goodToIncrement;
    }
    /**
     * Getter for the total number of views
     */
    public int getTotalViews() {
        return totalViews;
    }
    /**
     * Getter for the list of users that seen this movie
     */
    public List<String> getUsersThatSeen() {
        return usersThatSeen;
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
    /**
     * getter for favoriteOccurences
     */
    public int getFavoriteOccurences() {
        return favoriteOccurences;
    }
    /**
     * getter for views
     */
    public int getViews() {
        return views;
    }
    /**
     * method that calculates the average rating of this movie
     */
    public void calculateAverageRating() {
        Double sum = 0.0;
        for (Double rating : this.ratings) {
            sum += rating;
        }
        if (this.ratings.size() != 0) {
            this.averageRating = sum / (this.ratings.size());
        }
    }
    /**
     * method that increments the value of totalViews with the increment value
     * given as parameter to the method
     */
    public void incrementViews(final int increment) {
        int add = this.totalViews;
        this.totalViews = add + increment;
    }
    /**
     * method that calculates the views of this movie
     */
    public void calculateViews(final User user, final String title) {
        if (this.views == 0) {
            this.views += user.getHistory().get(title);
        } else {
            this.views++;
        }
    }
    /**
     * method that increments the value of favoriteOccurences
     */
    public void incrementFavoriteOccurences() {
        this.favoriteOccurences++;
    }
    /**
     * method that verifies if the user can rate a season
     * If yes, the rating is added
     */
    public String addRating(final User user, final Double grade) {
        String message = new String();
        // se verifica daca userul a vazut deja filmul -> apeleaza verifyIfIsSeen
        if (!(user.verifyIfIsSeen(this.getTitle()))) {
            // eroare -> nu a fost vazut
            String error = new String("error -> ");
            message = message + error;
            message = message + this.getTitle();
            String endString = new String(" is not seen");
            message = message + endString;
            return message;
        }
        if (userWithRatings.containsKey(user.getUsername())) {
            // eroare -> user a dat deja rating la acest film
            String error = new String("error -> ");
            message = message + error;
            message = message + this.getTitle();
            String endString = new String(" has been already rated");
            message = message + endString;
            return message;
        }
        // in acest caz se poate da rate -> succes
        user.getRatedMovies().add(this.getTitle());
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
     * method that adds a movie in a list if it has been rated
     */
    public void filterMoviesByRating(final List<Movie> filteredMovies) {
        if (this.averageRating != 0.0) {
            filteredMovies.add(this);
        }
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
