package main;

import java.util.ArrayList;
import java.util.List;
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
     * view method that tries to increment the number of views
     */
    public String view(final String title) {
        // caz cand a fost deja vazut
        if (verifyIfIsSeen(title)) {
            // increment the views
            Integer views = this.history.get(title);
            views += 1;
            this.history.remove(title);
            this.history.put(title, views);
        } else {
            // daca nu a mai fost deja vazut
            this.history.put(title, 1);
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
        // daca filmul a fost marcat ca si vazut
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
                String endString = new String(" was added as favourite");
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
     * firstUnseenVideo method that finds the first video unseen by the user from a list of videos
     */
    public String firstUnseenVideo(final List<Video> videos) {
        for (Video video : videos) {
            if (!(this.history.containsKey(video.getTitle()))) {
                return video.getTitle();
            }
        }
        return null;
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
