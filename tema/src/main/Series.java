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
//    private ArrayList<Integer> ratedSeasons;
    private Map<String, Double> userWithRatedSeasons;
    private int views;

    public Series(final String title, final ArrayList<String> cast,
                           final ArrayList<String> genres,
                           final int numberOfSeasons, final ArrayList<Season> seasons,
                           final int year) {
        super(title, year, cast, genres);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
        this.averageRating = 0.0;
        this.userWithRatedSeasons = new HashMap<String, Double>();
        this.views = 0;
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
    public double getAverageRating() {
        return averageRating;
    }
    public void incrementViews(final int increment) {
        this.views += increment;
    }

    public Double calculateAverageRatingSeason(Season season) {
        Double average = 0.0;
        List<Double> ratings = season.getRatings();
        if(ratings.size() == 0) {
            return 0.0;
        }
        for(Double rating : ratings) {
            average += rating;
        }
        return (average / (ratings.size()));
    }

    public void calculateAverageRatingSerial() {
        Double averageSerial = 0.0;
        for(Season season : this.seasons) {
            Double average = calculateAverageRatingSeason(season);
            averageSerial += average;
        }
        if(this.seasons.size() == 0) {
            this.averageRating = 0.0;
        } else {
            this.averageRating = averageSerial / (this.seasons.size());
        }
    }

    public String addRating(final User user, final Double grade, final int seasonNumber) {
        String message = new String();
        // se verifica daca userul a vazut deja serialul -> apeleaza verifyIfIsSeen
        if(!(user.verifyIfIsSeen(this.getTitle()))) {
            // eroare -> nu a fost vazut
            String error = new String("error -> ");
            message = message + error;
            message = message + this.getTitle();
            String endString = new String(" is not seen");
            message = message + endString;
            return message;
        }
        // se verifica daca userul a dat deja rate la sezon
        if(this.userWithRatedSeasons.containsKey(user.getUsername())) {
            // eroare -> user a dat deja rating la acest film
            String error = new String("error -> ");
            message = message + error;
            message = message + this.getTitle();
            String endString = new String(" has been already rated");
            message = message + endString;
            return message;
        }
        // in acest caz se poate da rate la serial
        this.userWithRatedSeasons.put(user.getUsername(), grade);
//        List<Double> ratings = this.seasons.get(seasonNumber - 1).getRatings();
//        ratings.add(grade);
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
