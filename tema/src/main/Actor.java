package main;
import actor.ActorsAwards;
import fileio.ActorInputData;

import java.rmi.ServerError;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
/**
 * Information about an actor, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public class Actor implements Comparable<Actor> {
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
    public Integer getTotalNumberOfAwards() {
        return totalNumberOfAwards;
    }
    public void filterForWords(final List<String> words, final List<Actor> filterActors) {
        boolean found = true;
        for (String word : words) {
            if(!(this.careerDescription.toLowerCase().contains(word))) {
                found = false;
                break;
            }
        }
        if(found) {
            filterActors.add(this);
        }
    }
    public void filterForAwards(final List<String> awards, final List<Actor> filterActors) {
        boolean found = true;
        for(String award : awards) {
            if(!(this.awards.containsKey(award))) {
                found = false;
                break;
            }
        }
        if(filterActors.size() != 0) {
            if(!found) {
                filterActors.remove(this);
            }
        } else {
            if(found) {
                filterActors.add(this);
            }
        }
    }
    public void calculateTotalAwards() {
        for(Map.Entry<ActorsAwards, Integer> entry : awards.entrySet()) {
            this.totalNumberOfAwards += entry.getValue();
        }
    }
    public Movie searchMovie(final String title, final List<Movie> movies) {
        for(Movie movie : movies) {
            if(movie.getTitle().equals(title)) {
                return movie;
            }
        }
        return null;
    }
    public Series searchSeries(final String title, final List<Series> series) {
        for(Series serial : series) {
            if(serial.getTitle().equals(title)) {
                return serial;
            }
        }
        return null;
    }
    public void calculateAverageGrade(final List<Movie> movies, final List<Series> series) {
        int count = 0;
        for(String video : this.filmography) {
            Movie movie = searchMovie(video, movies);
            Series serial = searchSeries(video, series);
            if(movie != null) {
                if(!(movie.getAverageRating().equals(0.0))) {
                    this.averageGrade += movie.getAverageRating();
                    count++;
                }
            } else if(serial != null) {
                if(!(serial.getAverageRating().equals(0.0))) {
                    this.averageGrade += serial.getAverageRating();
                    count++;
                }
            }
        }
//        return ("count e " + count);
        if(count != 0) {
            this.averageGrade = this.averageGrade / count;
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
    @Override
    public int compareTo(Actor actor) {
//        if (getCreatedOn() == null || u.getCreatedOn() == null) {
//            return 0;
//        }
        return averageGrade.compareTo(actor.averageGrade);
    }
}
