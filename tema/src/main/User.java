package main;

import java.util.ArrayList;
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
