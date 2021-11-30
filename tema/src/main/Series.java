package main;
import entertainment.Season;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Information about a tv show, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public final class Series extends Video {
    /**
     * Number of seasons
     */
    private final int numberOfSeasons;
    /**
     * Season list
     */
    private final ArrayList<Season> seasons;
    /**
     * The average of all ratings of the seasons of the serial
     */
    private Double averageRating;
    /**
     * The map where the key is the number of the season and the value
     * is the list of users that rated that season
     */
    private Map<Integer, ArrayList<String>> userWithRatedSeasons;
    /**
     * The views
     */
    private int views;
    /**
     * The total number of views
     */
    private int totalViews;
    /**
     * The total number of occurences in favorite lists
     */
    private int favoriteOccurences;
    /**
     * The total total duration of serial
     */
    private int totalDuration;
    /**
     * Represents a status -> tells if we can apply incrementViews method or not
     * If this method has been already applied for a show than the status is false
     * and can't be applied anymore
     */
    private boolean goodToIncrement;
    /**
     * A list of users that seen the serial
     */
    private List<String> usersThatSeen;
    public Series(final String title, final ArrayList<String> cast,
                  final ArrayList<String> genres,
                  final int numberOfSeasons, final ArrayList<Season> seasons,
                  final int year) {
        super(title, year, cast, genres);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
        this.averageRating = 0.0;
        this.userWithRatedSeasons = new HashMap<Integer, ArrayList<String>>();
        this.views = 0;
        this.totalDuration = 0;
        this.usersThatSeen = new ArrayList<>();
        this.totalViews = 0;
        this.goodToIncrement = true;
    }
    /**
     * Getter for the total number of views
     */
    public int getTotalViews() {
        return totalViews;
    }
    /**
     * Getter for goodToIncrement status
     */
    public boolean getGoodToIncrement() {
        return goodToIncrement;
    }
    /**
     * Method that sets the status with false value
     */
    public void setFalse() {
        this.goodToIncrement = false;
    }
    /**
     * Getter for usersThatSeen list
     */
    public List<String> getUsersThatSeen() {
        return usersThatSeen;
    }
    /**
     * getter for numberOfSeasons
     */
    public int getNumberSeason() {
        return numberOfSeasons;
    }
    /**
     * getter for seasons
     */
    public ArrayList<Season> getSeasons() {
        return seasons;
    }
    /**
     * getter for average of ratings
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
     * getter for totalDuration
     */
    public int getTotalDuration() {
        return totalDuration;
    }
    /**
     * getter for views
     */
    public int getViews() {
        return views;
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
     * method that increments the value of favoriteOccurences
     */
    public void incrementFavoriteOccurences() {
        this.favoriteOccurences++;
    }
    /**
     * method that calculates the average rating of a season
     */
    public Double calculateAverageRatingSeason(final Season season) {
        Double average = 0.0;
        List<Double> ratings = season.getRatings();
        if (ratings.size() == 0) {
            return 0.0;
        }
        for (Double rating : ratings) {
            average += rating;
        }
        return (average / (ratings.size()));
    }
    /**
     * method that calculates the total duration adding the durations of the seasons
     */
    public void calculateTotalDuration() {
        int duration = 0;
        for (Season season : this.seasons) {
            duration += season.getDuration();
        }
        this.totalDuration = duration;
    }
    /**
     * method that calculates the average rating based on the seasons
     */
    public void calculateAverageRatingSerial() {
        Double averageSerial = 0.0;
        for (Season season : this.seasons) {
            Double average = calculateAverageRatingSeason(season);
            averageSerial += average;
        }
        if (this.seasons.size() == 0) {
            this.averageRating = 0.0;
        } else {
            this.averageRating = averageSerial / (this.seasons.size());
        }
    }
    /**
     * method that verifies if the user can rate a season
     * If yes, the rating is added
     */
    public String addRating(final User user, final Double grade, final int seasonNumber) {
        String message = new String();
        // se verifica daca userul a vazut deja serialul -> apeleaza verifyIfIsSeen
        if (!(user.verifyIfIsSeen(this.getTitle()))) {
            // eroare -> nu a fost vazut
            String error = new String("error -> ");
            message = message + error;
            message = message + this.getTitle();
            String endString = new String(" is not seen");
            message = message + endString;
            return message;
        }
        // se verifica daca userul a dat deja rate la serial
            if (this.userWithRatedSeasons.containsKey(seasonNumber)) {
                if (this.userWithRatedSeasons.get(seasonNumber).contains(user.getUsername())) {
//              eroare -> user a dat deja rating la acest sezon
                String error = new String("error -> ");
                message = message + error;
                message = message + this.getTitle();
                String endString = new String(" has been already rated");
                message = message + endString;
                return message;
                } else {
                    this.userWithRatedSeasons.get(seasonNumber).add(user.getUsername());
                }
        } else { // sezonului nu i s-a aplicat pana acum rate
                this.userWithRatedSeasons.put(seasonNumber, new ArrayList<String>());
                this.userWithRatedSeasons.get(seasonNumber).add(user.getUsername());
            }
        // in acest caz se poate da rate la sezon
        user.getRatedMovies().add(this.getTitle());
        this.seasons.get(seasonNumber - 1).getRatings().add(grade);
        calculateAverageRatingSerial();
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
        return message;
    }
    /**
     * method that adds a serial in a list if it has been rated
     */
    public void filterSeriesByRating(final List<Series> filteredSeries) {
        if (this.averageRating != 0) {
            filteredSeries.add(this);
        }
    }
    /**
     * method that calculates the views of the serial
     */
    public void calculateViews(final User user, final String title) {
        if (this.views == 0) {
            this.views += user.getHistory().get(title);
        } else {
            this.views++;
        }
    }
    /**
     * toString method for this class
     */
    @Override
    public String toString() {
        return "SerialInputData{" + " title= "
                + super.getTitle() + " " + " year= "
                + super.getYear() + " cast {"
                + super.getCast() + " }\n" + " genres {"
                + super.getGenres() + " }\n "
                + " numberSeason= " + numberOfSeasons
                + ", seasons=" + seasons + "\n\n" + '}';
    }
}
