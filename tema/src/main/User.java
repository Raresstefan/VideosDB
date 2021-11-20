package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Information about an user, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public class User {
    /**
     * User's username
     */
    private final String username;
    /**
     * Subscription Type
     */
    private final String subscriptionType;
    /**
     * The history of the movies seen
     */
    private final Map<String, Integer> history;
    /**
     * The list of rated movies
     */
    private ArrayList<String> ratedMovies;
    /**
     * The list of rated seasons
     */
//    private Map<Integer, Integer> ratedSeasons;
    /**
     * Movies added to favorites
     */
    private final ArrayList<String> favoriteMovies;
    public User(final String username, final String subscriptionType,
                final Map<String, Integer> history,
                final ArrayList<String> favoriteMovies) {
        this.username = username;
        this.subscriptionType = subscriptionType;
        this.favoriteMovies = favoriteMovies;
        this.history = history;
        this.ratedMovies = new ArrayList<>();
//        this.ratedSeasons = new HashMap<Integer, Integer>();
    }

    /**
     * getter for username
     */
    public String getUsername() {
        return username;
    }
    /**
     * getter for history(map) -> based on what the user watched
     */
    public Map<String, Integer> getHistory() {
        return history;
    }
    /**
     * getter for subscriptionType
     */
    public String getSubscriptionType() {
        return subscriptionType;
    }
    /**
     * getter for favoriteMovies -> based on the user's favorite movies
     */
    public ArrayList<String> getFavoriteMovies() {
        return favoriteMovies;
    }
    /**
     * getter for ratedMovies
     */
    public ArrayList<String> getRatedMovies() {
        return ratedMovies;
    }
    /**
     * getter for ratedSeasons
     */
//    public Map<Integer, Integer> getRatedSeasons() {
//        return ratedSeasons;
//    }
    /**
     * function that verifies if a movie has been rated before
     * by the user
     */
    public boolean verifyIfIsRated(final String movieTitle) {
        for (String title : ratedMovies) {
            if(title.equals(movieTitle)) {
                return true;
            }
        }
        return false;
    }
    /**
     * function that tries to add the title of a movie
     * to the favorite movies list. If the movie already exists
     * int the list it returns false -> meaning that the operation
     * has not succeded
     */
    public boolean addVideoToFavorite(final String movieTitle) {
        for (String favorite : favoriteMovies) {
            if (favorite.equals(movieTitle)) {
                return false;
            }
        }
        favoriteMovies.add(movieTitle);
        return true;
    }
    /**
     * function that verifies if the user has watched the movie
     * at least one time. If not, it returns false -> operation
     * couldn't be executed
     */
    public boolean verifyIfIsSeen(final String movieTitle) {
        if (this.history.get(movieTitle) == null) {
            return false;
        }
        if (this.history.get(movieTitle) >= 0) {
            return true;
        }
        return false;
    }
    /**
     * tries to add movie to ratedMovies if it has been not rated before
     */
//    public String addToRatedMovies(final Movie movie, final Double grade) {
//        boolean verifyHasBeenSeen = verifyIfIsSeen(movie.getTitle());
//        boolean verifyHasBeenRated = verifyIfIsRated(movie.getTitle());
//        String message = new String();
//        if(!verifyHasBeenSeen) {
//            // eroare -> nu a fost vazut
//            String error = new String("error -> ");
//            message = message + error;
//            message = message + movie.getTitle();
//            String endString = new String(" is not seen");
//            message = message + endString;
//            return message;
//        }
//        if(verifyHasBeenRated) {
//            // eroare -> a fost deja facut rate
//            String error = new String("error -> ");
//            message = message + error;
//            message = message + movie.getTitle();
//            String endString = new String(" has been already rated");
//            message = message + endString;
//            return message;
//        }
//        this.ratedMovies.add(movie.getTitle());
//        String succes = new String("success -> ");
//        succes = succes + movie.getTitle();
//        String middleString = new String(" was rated with ");
//        String gradeString = grade.toString();
//        middleString = middleString + gradeString;
//        succes = succes + middleString;
//        String str = new String(" by ");
//        succes = succes + str;
//        succes = succes + this.username;
//        message = message + succes;
//        movie.getRatings().add(grade);
//        movie.calculateAverageRating();
//        return message;
//    }

//    public String addToRatedSeries(final Movie movie, final Double grade)
    /**
     * view method that tries to increment the number of views
     */
    public String view(final String title, final int increment) {
        // caz cand a fost deja vazut
        if (verifyIfIsSeen(title)) {
            // increment the views
            Integer views = this.history.get(title);
            views += increment;
        } else {
            // daca nu a mai fost deja vazut
            this.history.put(title, increment);
        }

        String message = new String("success -> ");
        message = message + title;
        String endString = new String(" was viewed with total views of ");
        String viewsString = this.history.get(title).toString();
        endString = endString + viewsString;
        message = message + endString;
        return  message;
    }
    /**
     * favorite method that tries to add a movie in the favoriteList
     */
    public String favorite(final String title) {
        // daca filmul a fost arcat ca si vazut
        if (verifyIfIsSeen(title)) {
            // verific daca este deja in lista de favorite
            if (!addVideoToFavorite(title)) {
                // cazul cand se afla deja in favorite
                String errorMessage = new String("error -> ");
                errorMessage = errorMessage + title;
                String endString = new String(" is already in favourite list");
                errorMessage = errorMessage + endString;
                return errorMessage;
            } else {
                // cazul cand nu se afla deja in favorite -> success
                String successMessage = new String("success -> ");
                successMessage = successMessage + title;
                String endString = new String(" was viewed with total views of ");
                String viewsString = this.history.get(title).toString();
                endString = endString + viewsString;
                successMessage = successMessage + endString;
                return successMessage;
            }
        } else {
            // cazul cand filmul nu este vazut
            String errorMessage = new String("error -> ");
            errorMessage = errorMessage + title;
            String endString = new String(" is not seen");
            errorMessage = errorMessage + endString;
            return errorMessage;
        }
    }
    /**
     * toString method for this class
     */
    @Override
    public String toString() {
        return "UserInputData{" + "username='"
                + username + '\'' + ", subscriptionType='"
                + subscriptionType + '\'' + ", history="
                + history + ", favoriteMovies="
                + favoriteMovies + '}';
    }
}
