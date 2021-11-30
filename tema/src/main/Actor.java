package main;
import actor.ActorsAwards;
import fileio.ActorInputData;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * Information about an actor, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public class Actor {
    /**
     * actor name
     */
    private String name;
    /**
     * description of the actor's career
     */
    private String careerDescription;
    /**
     * videos starring actor
     */
    private ArrayList<String> filmography;
    /**
     * awards won by the actor
     */
    private Map<ActorsAwards, Integer> awards;
    /**
     * average grade based on ratings from movies and series
     */
    private Double averageGrade;
    /**
     * total number of awards won by the actor
     */
    private Integer totalNumberOfAwards;
    public Actor(final ActorInputData actor) {
        this.name = actor.getName();
        this.careerDescription = actor.getCareerDescription();
        this.filmography = actor.getFilmography();
        this.awards = actor.getAwards();
        this.averageGrade = 0.0;
        this.totalNumberOfAwards = 0;
    }
    /**
     * getter for name
     */
    public String getName() {
        return name;
    }
    /**
     * setter for name
     */
    public void setName(final String name) {
        this.name = name;
    }
    /**
     * getter for filmography (list)
     */
    public ArrayList<String> getFilmography() {
        return filmography;
    }
    /**
     * setter for filmography (list)
     */
    public void setFilmography(final ArrayList<String> filmography) {
        this.filmography = filmography;
    }
    /**
     * getter for awards (map)
     */
    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }
    /**
     * getter for careerDescription
     */
    public String getCareerDescription() {
        return careerDescription;
    }
    /**
     * getter for averageGrade
     */
    public Double getAverageGrade() {
        return averageGrade;
    }
    /**
     * setter for careerDescription
     */
    public void setCareerDescription(final String careerDescription) {
        this.careerDescription = careerDescription;
    }
    /**
     * getter for total number of awards won
     */
    public Integer getTotalNumberOfAwards() {
        return totalNumberOfAwards;
    }
    /**
     * method that adds in a list of actors an actor, who has in the carrerDescription words
     * from a list given as parameter in the method
     */
    public void filterForWords(final List<String> words, final List<Actor> filterActors) {
        boolean found = true;
        String[] separatedString = this.careerDescription.toLowerCase().split("\\s+|,"
                +
                "\\s*|\\.\\s*|\\-");
        for (String word : words) {
            int ok  = 0;
            for (String string : separatedString) {
                if (string.equals(word)) {
                    ok = 1;
                }
            }
            if (ok == 0) {
                found = false;
                break;
            }
        }
        if (found) {
            filterActors.add(this);
        }
    }
    /**
     * method that adds in a list of actors an actor, who has awards
     * from a list given as parameter in the method
     */
    public void filterForAwards(final List<String> theAwards, final List<Actor> filterActors) {
        boolean found = true;
        for (String award : theAwards) {
            ActorsAwards awardEnumValue = ActorsAwards.valueOf(award);
            if (!(this.awards.containsKey(awardEnumValue))) {
                found = false;
                break;
            }
        }
        if (found) {
            filterActors.add(this);
        }
    }
    /**
     * method that calculates the total number of awards won by the actor
     */
    public void calculateTotalAwards() {
        Integer totalNumber = 0;
        for (Map.Entry<ActorsAwards, Integer> entry : awards.entrySet()) {
            totalNumber += entry.getValue();
        }
        this.totalNumberOfAwards = totalNumber;
    }
    /**
     * method that searches a movie in a list of movies and returns it if it is found
     */
    public Movie searchMovie(final String title, final List<Movie> movies) {
        for (Movie movie : movies) {
            if (movie.getTitle().equals(title)) {
                return movie;
            }
        }
        return null;
    }
    /**
     * method that searches a serial in a list of series and returns it if it is found
     */
    public Series searchSeries(final String title, final List<Series> series) {
        for (Series serial : series) {
            if (serial.getTitle().equals(title)) {
                return serial;
            }
        }
        return null;
    }
    /**
     * method that calculates the average grade of an actor
     */
    public void calculateAverageGrade(final List<Movie> movies, final List<Series> series) {
        int count = 0;
        Double average = 0.0;
        for (String video : this.filmography) {
            Movie movie = searchMovie(video, movies);
            Series serial = searchSeries(video, series);
            if (movie != null) {

                if (!(movie.getAverageRating().equals(0.0))) {
                    average += movie.getAverageRating();
                    count++;
                }
            } else if (serial != null) {

                if (!(serial.getAverageRating().equals(0.0))) {
                    average += serial.getAverageRating();
                    count++;
                }
            }
        }
        if (count != 0) {
            this.averageGrade = average / count;
        }
    }
    /**
     * toString method for this class
     */
    @Override
    public String toString() {
        return "ActorInputData{"
                + "name='" + name + '\''
                + ", careerDescription='"
                + careerDescription + '\''
                + ", filmography=" + filmography + '}';
    }
}
